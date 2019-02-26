package ac.cn.saya.laboratory.persistent.service;
import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import ac.cn.saya.laboratory.persistent.dao.TransactionReadDAO;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: TransactionReadService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/1 22:49
 * @Description:财政业务相关-对外只读业务层接口
 */
@Service("transactionReadService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true,rollbackFor=Exception.class)
public class TransactionReadService{

    private static Logger logger = LoggerFactory.getLogger(TransactionReadService.class);

    @Resource
    @Qualifier("transactionReadDAO")
    private TransactionReadDAO transactionReadDAO;

    /**
     * 获取所有交易类别数据
     *
     * @return
     */
    public List<TransactionTypeEntity> selectTransactionType(){
        List<TransactionTypeEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionType();
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("查询交易类别时发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 查看流水
     * 根据用户、类型、日期
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionPage(TransactionListEntity entity) {
        List<TransactionListEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("查看流水时发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 查看流水总数
     * 根据用户、类型、日期
     * @param entity
     * @return
     */
    public Long selectTransactionCount(TransactionListEntity entity) {
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionCount(entity);
        }catch (Exception e)  {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("查看流水总数总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * 查看流水明细
     *
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionInfoPage(TransactionInfoEntity entity) {
        List<TransactionInfoEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionInfoPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("查看流水明细发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 查看流水明细总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionInfoCount(TransactionInfoEntity entity) {
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionInfoCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("查看查看流水明细总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * 查询详细的流水明细
     *
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionFinalPage(TransactionListEntity entity) {
        List<TransactionInfoEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionFinalPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("查询详细的流水明细发生异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 查询详细的流水明细总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionFinalCount(TransactionListEntity entity) {
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionFinalCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("查询详细的流水明细总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * 按天分页统计财务报表
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForDayPage(TransactionListEntity entity){
        List<TransactionListEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionForDayPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("按天分页统计财务报表异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 按天统计财务报表流水总数
     * @param entity
     * @return
     */
    public Long selectTransactionForDayCount(TransactionListEntity entity){
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionForDayCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("按天统计财务报表流水总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * 按月分页统计（只统计到上月的最后一天）
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForMonthPage(TransactionListEntity entity){
        List<TransactionListEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionForMonthPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("按月分页统计（只统计到上月的最后一天）异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 按月统计（只统计到上月的最后一天）总数
     * @param entity
     * @return
     */
    public Long selectTransactionForMonthCount(TransactionListEntity entity){
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionForMonthCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("按月统计（只统计到上月的最后一天）总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

    /**
     * 按年分页统计（只统计到上一年的最后一天）
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForYearPage(TransactionListEntity entity){
        List<TransactionListEntity> list = new ArrayList<>();
        try
        {
            list = transactionReadDAO.selectTransactionForYearPage(entity);
            if(list.size() <= 0) {
                list = null;
            }
        }catch (Exception e) {
            list = null;
            logger.error("按年分页统计（只统计到上一年的最后一天）异常："+ Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return list;
    }

    /**
     * 按年统计（只统计到上一年的最后一天）总数
     * @param entity
     * @return
     */
    public Long selectTransactionForYearCount(TransactionListEntity entity){
        Long total = null;
        try
        {
            total = transactionReadDAO.selectTransactionForYearCount(entity);
        }catch (Exception e) {
            total = Long.valueOf(ResultEnum.ERROP.getCode());
            logger.error("按年统计（只统计到上一年的最后一天）总数时发生异常："+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
        return total;
    }

}
