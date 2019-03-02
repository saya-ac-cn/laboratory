package ac.cn.saya.laboratory.service;

/**
 * @Title: SystemService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-01 20:24
 * @Description:
 * 处理系统级别的业务
 * 包括：
 * 1、数据库备份
 * 2、发送邮件
 */

public interface SystemService {

    /**
     * @描述 备份数据库
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-01
     * @修改人和其它信息
     */
    public Boolean backupDatabase();

    /**
     * @描述 删除数据库备份
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-02
     * @修改人和其它信息
     */
    public Boolean deleteBackup();

}
