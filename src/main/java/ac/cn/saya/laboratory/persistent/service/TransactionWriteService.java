package ac.cn.saya.laboratory.persistent.service;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.persistent.dao.TransactionWriteDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Log4jUtils;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Title: TransactionWriteService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/12/27 22:49
 * @Description:财政业务相关-对外可修改业务层接口
 */

@Service("transactionWriteService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = false, rollbackFor=Exception.class)
public class TransactionWriteService{

    private static Logger logger = LoggerFactory.getLogger(TransactionWriteService.class);


    @Resource
    @Qualifier("transactionWriteDAO")
    private TransactionWriteDAO transactionWriteDAO;

    /**
     * @param entity
     * @描述 写入到财政明细表
     * @参数 [entity]
     * @返回值 java.lang.Integer 返回写入状态标志位
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer insertTransactionInfo(TransactionInfoEntity entity) {
        Integer flog = null;
        try
        {
            flog = transactionWriteDAO.insertTransactionInfo(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("写入到财政明细表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 写入到财政父表
     * @参数 [entity]
     * @返回值 java.lang.Integer 返回主键回填的值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer insertTransactionList(TransactionListEntity entity) {
        Integer tradeId = null;
        try
        {
            tradeId = transactionWriteDAO.insertTransactionList(entity);
            if(tradeId > 0) {
                // 取出主键回填的值
                tradeId = entity.getTradeId();
            }
        }catch (Exception e) {
            tradeId = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("写入到财政父表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return tradeId;
    }

    /**
     * @param entity
     * @描述 修改财政明细表
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer updateTransactionInfo(TransactionInfoEntity entity) {
        Integer flog = null;
        try
        {
            flog = transactionWriteDAO.updateTransactionInfo(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("修改财政明细表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param entity
     * @描述 修改财政父表
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer updateTransactionList(TransactionListEntity entity) {
        Integer flog = null;
        try
        {
            flog = transactionWriteDAO.updateTransactionList(entity);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("修改财政父表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param id
     * @param source
     * @描述 删除财政明细表
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer deleteTransactionInfo(Integer id, String source) {
        Integer flog = null;
        try
        {
            flog = transactionWriteDAO.deleteTransactionInfo(id);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除财政明细表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }

    /**
     * @param tradeId
     * @param source
     * @描述 删除财政父表
     * @参数 [entity]
     * @返回值 java.lang.Integer
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2018/12/27
     * @修改人和其它信息
     */
    public Integer deleteTransactionList(Integer tradeId, String source) {
        Integer flog = null;
        try
        {
            flog = transactionWriteDAO.deleteTransactionList(tradeId, source);
        }catch (Exception e) {
            flog = ResultEnum.UNKONW_ERROR.getCode();
            logger.error("删除财政父表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return flog;
    }
}
