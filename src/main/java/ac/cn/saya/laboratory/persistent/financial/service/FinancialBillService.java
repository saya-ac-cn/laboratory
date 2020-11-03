package ac.cn.saya.laboratory.persistent.financial.service;

import ac.cn.saya.laboratory.entity.BillOfAmountEntity;
import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.financial.dao.BillDAO;
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
import java.util.List;

/**
 * @Title: FinancialBillService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/21 22:30
 * @Description:财政账单
 */
@Service("financialBillService")
@Transactional(value = "financialTransactionManager",readOnly = true,propagation= Propagation.REQUIRED, isolation= Isolation.SERIALIZABLE, rollbackFor= MyException.class)
public class FinancialBillService {

    private static final Logger logger = LoggerFactory.getLogger(FinancialBillService.class);

    @Resource
    @Qualifier("billDAO")
    private BillDAO billDAO;

    /**
     * @描述 按天分页查询账单
     * @参数  [param] 其中时间参数 为月格式：2020-10
     * @返回值  java.util.List<ac.cn.saya.laboratory.entity.BillOfDayEntity>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public List<BillOfDayEntity> getBillByDay(TransactionListEntity param){
        try {
            return billDAO.queryBillByDay(param);
        } catch (Exception e) {
            logger.error("按天分页查询账单失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 统计指定月份的总收入和支出
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.entity.BillOfDayEntity
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public BillOfDayEntity totalBalance(BillOfDayEntity param){
        try {
            return billDAO.totalBalance(param);
        } catch (Exception e) {
            logger.error("统计指定月份的总收入和支出失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 统计指定月份中各摘要的收支情况（flag=-1）或收入（flag=1）
     * @参数  [tradeDate:月份, source:所属用户账单, flag:收支 标志]
     * @返回值  java.util.List<ac.cn.saya.laboratory.entity.BillOfAmountEntity>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public List<BillOfAmountEntity> totalBillByAmount(String tradeDate,String source,int flag){
        try {
            return billDAO.totalBillByAmount(tradeDate, source, flag);
        } catch (Exception e) {
            logger.error("统计指定月份中各摘要的收支情况失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定月份中支出（flag=-1）或收入（flag=1）的排行
     * @参数  [tradeDate:月份, source:所属用户账单, flag:收支 标志]
     * @返回值  java.util.List<ac.cn.saya.laboratory.entity.TransactionListEntity>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public List<TransactionListEntity> getBillBalanceRank(String tradeDate,String source,int flag){
        try {
            return billDAO.queryBillBalanceRank(tradeDate, source, flag);
        } catch (Exception e) {
            logger.error("查询指定月份中支出或收入排行失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定月份中，某一摘要类型的收支数据
     * @参数  [param]
     * @返回值  java.util.List<ac.cn.saya.laboratory.entity.TransactionListEntity>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public List<TransactionListEntity> getBillByAmount(TransactionListEntity param){
        try {
            return billDAO.queryBillByAmount(param);
        } catch (Exception e) {
            logger.error("查询指定月份中，某一摘要类型的收支数据失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询账单明细
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.entity.TransactionListEntity
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    public TransactionListEntity getBillDetail(TransactionListEntity param){
        try {
            return billDAO.queryBillDetail(param);
        } catch (Exception e) {
            logger.error("查询账单明细失败:"+Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
