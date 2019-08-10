package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import ac.cn.saya.laboratory.entity.PlanEntity;
import ac.cn.saya.laboratory.persistent.service.BackupLogService;
import ac.cn.saya.laboratory.persistent.service.PlanService;
import ac.cn.saya.laboratory.service.SystemService;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.RandomUtil;
import ac.cn.saya.laboratory.tools.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ClassUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Title: SystemServiceImpl
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-01 20:27
 * @Description:
 */

@Service("systemServiceImpl")
public class SystemServiceImpl implements SystemService {

    private static Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Value("${backup.dburl}")
    private String dburl;

    @Value("${backup.dbname}")
    private String dbname;

    @Value("${backup.mysqlbin}")
    private String mysqlbin;

    @Value("${backup.mail}")
    private String mail;

    @Value("${backup.savemonth}")
    private Integer savemonth;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Resource
    @Qualifier("backupLogService")
    private BackupLogService backupLogService;

    @Resource
    @Qualifier("planService")
    private PlanService planService;

    @Autowired
    private MailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * @描述 备份数据库、每天3点备份一次
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-01
     * @修改人和其它信息
     */
    @Override
    // 每天3点执行
    @Scheduled(cron = "0 0 3 * * ?")
    // 每隔5分钟执行一次
    //@Scheduled(cron = "0 0/1 * * * ?")
    public Boolean backupDatabase() {
        try {
            Date currentTime = new Date();
            SimpleDateFormat mailformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String executeTime = mailformat.format(currentTime);
            //url路径 files/database
            String urlPath = File.separator + "files" + File.separator + "database";
            //上传文件路径-/database/目录下当天的文件夹
            String backUppath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + urlPath;
            //保存的文件名
            String backUpName = RandomUtil.getRandomFileName() + ".sql";
            // 保存本次执行任务，必须要在备份前保存本次任务，否则将出现异常
            backupLogService.insertBackup(urlPath + File.separator + backUpName);
            Boolean flog = exportDatabaseTool(this.dburl, this.username, this.password, backUppath, backUpName, this.dbname, this.mysqlbin);
            if (flog) {
                // 记录此次的备份情况
                sendBackUpMail(executeTime, "成功", (urlPath + File.separator + backUpName));
            } else {
                sendBackUpMail(executeTime, "失败", "-");
            }
        } catch (InterruptedException e) {
            logger.error("备份数据库计划异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return false;
    }

    /**
     * @描述 删除数据库备份 每月1日凌晨1点执行
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-02
     * @修改人和其它信息
     */
    @Override
    @Scheduled(cron = "0 0 1 1 * ?")
    public Boolean deleteBackup() {
        //查询一个月以前的数据库备份总数
        try {
            BackupLogEntity queryEntity = new BackupLogEntity();
            queryEntity.setSaveMonth(savemonth);
            Long count = backupLogService.getBackupCount(queryEntity);
            if (count > 0) {
                // 执行删除计划
                queryEntity.setStartLine(0);
                queryEntity.setEndLine(count.intValue());
                // 读取备份记录
                List<BackupLogEntity> list = backupLogService.getBackupPagin(queryEntity);
                // 删除磁盘文件
                for (BackupLogEntity item : list) {
                    UploadUtils.deleteFile(item.getUrl());
                }
                // 删除数据库激励
                backupLogService.deleteBackup(queryEntity);
            }
        } catch (Exception e) {
            logger.error("清理备份数据库计划异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return false;
    }

    /**
     * @描述 计划安排邮件发送提醒（每天4点执行）
     * @参数 []
     * @返回值 java.lang.Boolean
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-06-03
     * @修改人和其它信息
     */
    @Override
    // 每天4点执行
    @Scheduled(cron = "0 0 4 * * ?")
    public Boolean remindPlan() {
        List<PlanEntity> list = planService.getTodayPlanList();
        try {
            if (list == null || list.size() <= 0) {
                // 今日无计划安排
                return false;
            } else {
                // 今日有计划安排
                for (PlanEntity item : list) {
                    sendRemndPlanMail(item.getSource(), item.getCreatetime(), item.getDescribe());
                }
                return true;
            }
        } catch (Exception e) {
            logger.error("日程安排邮件发送定时任务异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return false;
    }

    /**
     * Java代码实现MySQL数据库导出
     *
     * @param hostIP       MySQL数据库所在服务器地址IP
     * @param userName     进入数据库所需要的用户名
     * @param password     进入数据库所需要的密码
     * @param savePath     数据库导出文件保存路径
     * @param fileName     数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @param mysqlBin     Mysql的bin目录
     * @return 返回true表示导出成功，否则返回false。
     * @author Saya
     */
    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName, String mysqlBin) throws InterruptedException {
        File saveFile = new File(savePath);
        // 如果目录不存在
        if (!saveFile.exists()) {
            // 创建文件夹
            saveFile.mkdirs();
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            Process process = Runtime.getRuntime().exec(mysqlBin + "mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
            printWriter.flush();
            //0 表示线程正常终止。
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (IOException e) {
            logger.error("备份数据库异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @描述 发送数据库备份结果报告邮件
     * @参数 executeTime 执行时间
     * @参数 executeResult 执行结果
     * @参数 saveUrl 保存位置
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-01
     * @修改人和其它信息
     */
    public void sendBackUpMail(String executeTime, String executeResult, String saveUrl) {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("executeTime", executeTime);
        context.setVariable("executeResult", executeResult);
        context.setVariable("saveUrl", saveUrl);
        context.setVariable("sendTime", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
        try {
            String emailContent = templateEngine.process("mail/backUpDB", context);
            mailService.sendHtmlMail(mail, "数据库备份结果报告", emailContent);
        } catch (Exception e) {
            logger.error("邮件发送异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
    }

    /**
     * @描述 发送计划安排邮件提醒
     * @参数 [userName, createTime, planContent]
     * @返回值 void
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-06-03
     * @修改人和其它信息
     */
    public void sendRemndPlanMail(String userName, String createTime, String planContent) {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("createTime", createTime);
        context.setVariable("planContent", planContent);
        context.setVariable("sendTime", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
        try {
            String emailContent = templateEngine.process("mail/remindPlan", context);
            mailService.sendHtmlMail(mail, "今日计划安排提醒", emailContent);
        } catch (Exception e) {
            logger.error("邮件发送异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
    }

}
