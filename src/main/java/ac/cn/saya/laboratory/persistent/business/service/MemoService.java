package ac.cn.saya.laboratory.persistent.business.service;

import ac.cn.saya.laboratory.entity.MemoEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.business.dao.BusinessBatchDAO;
import ac.cn.saya.laboratory.persistent.business.dao.MemoDAO;
import ac.cn.saya.laboratory.tools.AesUtil;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Title: MemoService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-09-21 21:13
 * @Description: 便笺对外接口
 */
@Service("memoService")
@Transactional(value = "primaryTransactionManager",readOnly = false,propagation= Propagation.REQUIRED, isolation= Isolation.REPEATABLE_READ, rollbackFor=MyException.class)
public class MemoService {

    private static Logger logger = LoggerFactory.getLogger(MemoService.class);

    @Resource
    @Qualifier("businessBatchDAO")
    private BusinessBatchDAO batchDAO;

    @Resource
    private MemoDAO memoDAO;

    /**
     * @描述 创建便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    public int insert(MemoEntity entity) {
        try {
            entity.setContent(AesUtil.Encrypt(entity.getContent()));
            return memoDAO.insert(entity);
        } catch (Exception e) {
            logger.error("创建便笺异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }


    /**
     * @描述 查询便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public MemoEntity getOne(MemoEntity entity) {
        try {
            MemoEntity result = memoDAO.query(entity);
            if (null != result && !StringUtils.isEmpty(result.getContent())) {
                result.setContent(AesUtil.Decrypt(result.getContent()));
            }
            return result;
        } catch (Exception e) {
            logger.error("查询便笺异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 修改便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    public int update(MemoEntity entity) {
        try {
            entity.setContent(AesUtil.Encrypt(entity.getContent()));
            return memoDAO.update(entity);
        } catch (Exception e) {
            logger.error("修改便笺异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 删除便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    public int delete(MemoEntity entity) {
        try {
            return memoDAO.delete(entity);
        } catch (Exception e) {
            logger.error("删除便笺异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取分页后的便笺
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public List<MemoEntity> getPage(MemoEntity entity) {
        try {
            List<MemoEntity> list = memoDAO.queryPage(entity);
            if (list.size() <= 0) {
                list = null;
            }
            return list;
        } catch (Exception e) {
            logger.error("获取分页后的便笺列表异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 获取便笺总数
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-09-21
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    public Long getCount(MemoEntity entity) {
        try {
            return memoDAO.queryCount(entity);
        } catch (Exception e) {
            logger.error("获取便笺总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询近半年便笺发布情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, Object> countPre6Memo(String user) {
        try {
            return batchDAO.countPre6Memo(user);
        } catch (Exception e) {
            logger.error("查询近半年便笺发布情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

}
