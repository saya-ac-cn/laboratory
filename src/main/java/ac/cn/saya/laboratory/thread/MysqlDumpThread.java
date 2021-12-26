package ac.cn.saya.laboratory.thread;

import ac.cn.saya.laboratory.tools.CurrentLineInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * @Title: MysqlDumpThread
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-05-03 15:55
 * @Description: 多线程 异步 备份数据库
 */

public class MysqlDumpThread implements Callable<Boolean> {

    /**
     * MySQL数据库所在服务器地址IP
     */
    private String hostIP;

    /**
     * 进入数据库所需要的用户名
     */
    private String userName;

    /**
     * 进入数据库所需要的密码
     */
    private String password;

    /**
     * 数据库导出文件保存路径
     */
    private String savePath;

    /**
     * 数据库导出文件文件名
     */
    private String fileName;

    /**
     * 要导出的数据库名
     */
    private String databaseNames;

    /**
     * Mysql的bin目录
     */
    private String mysqlBin;

    public MysqlDumpThread(String hostIP, String userName, String password, String savePath, String fileName, String databaseNames, String mysqlBin) {
        this.hostIP = hostIP;
        this.userName = userName;
        this.password = password;
        this.savePath = savePath;
        this.fileName = fileName;
        this.databaseNames = databaseNames;
        this.mysqlBin = mysqlBin;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Boolean call() throws Exception {
        boolean result = false;
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
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), StandardCharsets.UTF_8));
            StringBuilder command = new StringBuilder(mysqlBin).append("mysqldump");
            command.append(" -h").append(hostIP);
            command.append(" -u").append(userName);
            command.append(" -p").append(password);
            /// 设置字符集
            command.append(" --set-charset=UTF8");
            /// 导出存储过程以及自定义函数
            command.append(" --routines");
            /// 要导出的数据库
            command.append(" --databases ").append(databaseNames);
            Process process = Runtime.getRuntime().exec(command.toString());
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
            printWriter.flush();
            //0 表示线程正常终止。
            if (process.waitFor() == 0) {
                result = true;
            }
        } catch (IOException e) {
            CurrentLineInfo.printCurrentLineInfo("备份数据库异常",e, MysqlDumpThread.class);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            return result;
        }
    }
}
