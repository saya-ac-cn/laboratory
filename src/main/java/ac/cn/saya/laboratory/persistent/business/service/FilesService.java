package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.FilesEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.BusinessBatchDAO;
import ac.cn.saya.laboratory.persistent.business.dao.FilesDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: FilesService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/15 23:05
 * @Description:
 */

@Service("filesService")
@Transactional(value = "primaryTransactionManager",readOnly = false,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class FilesService {


    private static Logger logger = LoggerFactory.getLogger(FilesService.class);

    @Resource
    @Qualifier("businessBatchDAO")
    private BusinessBatchDAO batchDAO;

    @Resource
    private FilesDAO filesDAO;

    /***
     * @描述 添加文件上传记录
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    public Integer insertFile(FilesEntity entity) {
        try {
            return filesDAO.insertFile(entity);
        } catch (Exception e) {
            logger.error("添加文件上传记录异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 保存修改文件记录
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    public Integer updateFile(FilesEntity entity) {
        try {
            return filesDAO.updateFile(entity);
        } catch (Exception e) {
            logger.error("保存修改文件记录异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 删除文件记录
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    public Integer deleteFile(FilesEntity entity) {
        try {
            return filesDAO.deleteFile(entity);
        } catch (Exception e) {
            logger.error("删除文件记录异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询分页后的文件列表
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<FilesEntity> getFilePage(FilesEntity entity) {
        List<FilesEntity> list = new ArrayList<>();
        try {
            list = filesDAO.getFilePage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查询分页后的文件列表发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询文件总数
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getFileCount(FilesEntity entity) {
        try {
            return filesDAO.getFileCount(entity);
        } catch (Exception e) {
            logger.error("查询文件总数异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取一条文件信息
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019/1/15
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public FilesEntity getOneFile(FilesEntity entity) {
        try {
            return filesDAO.getOneFile(entity);
        } catch (Exception e) {
            logger.error("获取一条文件信息异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年文件上传情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, Object> countPre6Files(String user) {
        try {
            return batchDAO.countPre6Files(user);
        } catch (Exception e) {
            logger.error("查询近半年文件上传情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
