package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: BackupLogDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-02-28 21:37
 * @Description:
 * 平台数据库备份记录
 */

@Mapper
public interface BackupLogDAO {

    /**
     * @描述 新增备份记录
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019/1/11
     * @修改人和其它信息
     */
    public Integer insertBackup(@Param("backupUrl") String backupUrl);

    /**
     * @描述 删除备份数据
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-02-28
     * @修改人和其它信息
     */
    public Integer deleteBackup(BackupLogEntity entity);

    /**
     * @描述 分页查看备份记录
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-02-28
     * @修改人和其它信息
     */
    public List<BackupLogEntity> getBackupPagin(BackupLogEntity entity);

    /**
     * @描述 查看备份记录总数
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-02-28
     * @修改人和其它信息
     */
    public Long getBackupCount(BackupLogEntity entity);

    /**
     * @描述 查询单条备份记录
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-02-28
     * @修改人和其它信息
     */
    public BackupLogEntity getBackupOne(BackupLogEntity entity);

}
