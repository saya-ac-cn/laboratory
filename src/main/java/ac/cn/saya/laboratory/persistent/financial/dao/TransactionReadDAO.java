package ac.cn.saya.laboratory.persistent.financial.dao;

import ac.cn.saya.laboratory.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Title: TransactionReadDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/1 22:06
 * @Description: 财政数据库只读DAO
 */

@Mapper
public interface TransactionReadDAO {

    /**
     * 获取交易类别表
     *
     * @return
     */
    public List<TransactionTypeEntity> selectTransactionType();

    /**
     * 获取交易摘要表
     *
     * @return
     */
    public List<TransactionAmountEntity> selectTransactionAmount();

    /**
     * 获取交易总览
     *
     * @param entity
     * @return
     */
    public TransactionListEntity selectTransactionList(TransactionListEntity entity);

    /**
     * 查看流水
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionPage(TransactionListEntity entity);

    /**
     * 查看流水总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionCount(TransactionListEntity entity);

    /**
     * 查看流水明细
     *
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionInfoPage(TransactionInfoEntity entity);

    /**
     * 查看流水明细总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionInfoCount(TransactionInfoEntity entity);

    /**
     * 查看收支明细（明细记录折叠存）
     * @param entity
     * @return
     */
    public TransactionListEntity selectTransactionDetail(TransactionListEntity entity);

    /**
     * 分页查看收支明细（明细记录折叠存）
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionDetailPage(TransactionListEntity entity);

    /**
     * 分页查看收支明细总数（明细记录折叠存）
     * @param entity
     * @return
     */
    public Long selectTransactionDetailCount(TransactionListEntity entity);

    /**
     * 按天分页统计财务报表
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForDayPage(TransactionListEntity entity);

    /**
     * 按天统计财务报表流水总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForDayCount(TransactionListEntity entity);

    /**
     * 按月分页统计（只统计到上月的最后一天）
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForMonthPage(TransactionListEntity entity);

    /**
     * 按月统计（只统计到上月的最后一天）总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForMonthCount(TransactionListEntity entity);

    /**
     * 按年分页统计（只统计到上一年的最后一天）
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForYearPage(TransactionListEntity entity);

    /**
     * 按年统计（只统计到上一年的最后一天）总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForYearCount(TransactionListEntity entity);

    /**
     * 统计指定月份的总收入和支出
     * @param param
     * @return
     */
    public BillOfDayEntity totalBalance(BillOfDayEntity param);

    /**
     * 统计指定月份中各摘要的收支情况（flag=-1）或收入（flag=1）
     * @param tradeDate 月份
     * @param source    所属用户账单
     * @param flag 收支 标志
     * @return
     */
    public List<BillOfAmountEntity> totalBillByAmount(@Param("tradeDate") String tradeDate, @Param("source") String source, @Param("flag") int flag);

}
