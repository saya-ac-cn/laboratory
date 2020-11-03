package ac.cn.saya.laboratory.persistent.financial.dao;

import ac.cn.saya.laboratory.entity.BillOfAmountEntity;
import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账单DAO（主要由小程序发起）
 * @Title: BillDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/18 22:45
 * @Description: 本类下只提供查询业务
 */

@Mapper
public interface BillDAO {

    /**
     * 按天分页查询账单
     * @param param 其中时间参数 为月格式：2020-10
     * @return
     */
    public List<BillOfDayEntity> queryBillByDay(TransactionListEntity param);

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
    public List<BillOfAmountEntity> totalBillByAmount(@Param("tradeDate") String tradeDate,@Param("source") String source,@Param("flag") int flag);

    /**
     * 查询指定月份中支出（flag=-1）或收入（flag=1）的排行
     * @param tradeDate 月份
     * @param source    所属用户账单
     * @param flag 收支 标志
     * @return
     */
    public List<TransactionListEntity> queryBillBalanceRank(@Param("tradeDate") String tradeDate,@Param("source") String source,@Param("flag") int flag);

    /**
     * 查询指定月份中，某一摘要类型的收支数据
     * @param param
     * @return
     */
    public List<TransactionListEntity> queryBillByAmount(TransactionListEntity param);

    /**
     * 查询账单明细
     * @param param
     * @return
     */
    public TransactionListEntity queryBillDetail(TransactionListEntity param);

}
