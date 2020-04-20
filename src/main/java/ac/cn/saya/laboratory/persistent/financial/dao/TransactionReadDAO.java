package ac.cn.saya.laboratory.persistent.financial.dao;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: TransactionReadDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/1 22:06
 * @Description:
 * 财政数据库只读DAO
 */

@Mapper
@Repository("transactionReadDAO")
public interface TransactionReadDAO {

    /**
     * 获取交易类别表
     * @return
     */
    public List<TransactionTypeEntity> selectTransactionType();

    /**
     * 查看流水
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionPage(TransactionListEntity entity);

    /**
     * 查看流水总数
     * @param entity
     * @return
     */
    public Long selectTransactionCount(TransactionListEntity entity);

    /**
     * 查看流水明细
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionInfoPage(TransactionInfoEntity entity);

    /**
     * 查看流水明细总数
     * @param entity
     * @return
     */
    public Long selectTransactionInfoCount(TransactionInfoEntity entity);

    /**
     * 查询详细的流水明细
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionFinalPage(TransactionListEntity entity);

    /**
     * 查询详细的流水明细总数
     * @param entity
     * @return
     */
    public Long selectTransactionFinalCount(TransactionListEntity entity);

    /**
     * 按天分页统计财务报表
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForDayPage(TransactionListEntity entity);

    /**
     * 按天统计财务报表流水总数
     * @param entity
     * @return
     */
    public Long selectTransactionForDayCount(TransactionListEntity entity);

    /**
     * 按月分页统计（只统计到上月的最后一天）
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForMonthPage(TransactionListEntity entity);

    /**
     * 按月统计（只统计到上月的最后一天）总数
     * @param entity
     * @return
     */
    public Long selectTransactionForMonthCount(TransactionListEntity entity);

    /**
     * 按年分页统计（只统计到上一年的最后一天）
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForYearPage(TransactionListEntity entity);

    /**
     * 按年统计（只统计到上一年的最后一天）总数
     * @param entity
     * @return
     */
    public Long selectTransactionForYearCount(TransactionListEntity entity);

}
