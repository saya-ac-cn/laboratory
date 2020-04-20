package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.BackupLogDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: NotesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/19 18:52
 * @Description: 平台数据库备份日志记录
 */
@Service("backupLogService")
public class BackupLogService {

    private static Logger logger = LoggerFactory.getLogger(BackupLogService.class);

    @Resource
    @Qualifier("backupLogDAO")
    private BackupLogDAO backupLogDAO;

    /**
     * @描述 新增备份记录
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer insertBackup(String backupUrl) {
        try {
            return backupLogDAO.insertBackup(backupUrl);
        } catch (Exception e) {
            logger.error("新增备份记录异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }


    /**
     * @描述 删除备份数据
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Integer deleteBackup(BackupLogEntity entity) {
        try {
            return backupLogDAO.deleteBackup(entity);
        } catch (Exception e) {
            logger.error("删除备份数据异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询单条备份记录
     * @参数 [entity]
     * @返回值 ac.cn.saya.datacenter.entity.ApiEntity
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/12
     * @修改人和其它信息
     */
    public BackupLogEntity getOneBackup(BackupLogEntity entity) {
        try {
            return backupLogDAO.getBackupOne(entity);
        } catch (Exception e) {
            logger.error("查询单条备份记录异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 分页查看备份记录
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public List<BackupLogEntity> getBackupPagin(BackupLogEntity entity) {
        List<BackupLogEntity> list = new ArrayList<>();
        try {
            list = backupLogDAO.getBackupPagin(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("分页查看备份记录发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查看备份记录总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/11
     * @修改人和其它信息
     */
    public Long getBackupCount(BackupLogEntity entity) {
        try {
            return backupLogDAO.getBackupCount(entity);
        } catch (Exception e) {
            logger.error("查看备份记录总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
