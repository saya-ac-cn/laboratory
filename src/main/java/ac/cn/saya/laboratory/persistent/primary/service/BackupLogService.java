package ac.cn.saya.laboratory.persistent.primary.service;

import ac.cn.saya.laboratory.entity.BackupLogEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.primary.dao.BackupLogDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(value = "primaryTransactionManager", readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = MyException.class)
public class BackupLogService {

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
            CurrentLineInfo.printCurrentLineInfo("新增备份记录异常", e, BackupLogService.class);
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
            CurrentLineInfo.printCurrentLineInfo("删除备份数据异常", e, BackupLogService.class);
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
    @Transactional(readOnly = true)
    public BackupLogEntity getOneBackup(BackupLogEntity entity) {
        try {
            return backupLogDAO.getBackupOne(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询单条备份记录异常", e, BackupLogService.class);
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
    @Transactional(readOnly = true)
    public List<BackupLogEntity> getBackupPagin(BackupLogEntity entity) {
        List<BackupLogEntity> list = new ArrayList<>();
        try {
            list = backupLogDAO.getBackupPagin(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("分页查看备份记录发生异常", e, BackupLogService.class);
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
    @Transactional(readOnly = true)
    public Long getBackupCount(BackupLogEntity entity) {
        try {
            return backupLogDAO.getBackupCount(entity);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查看备份记录总数时发生异常", e, BackupLogService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
