package ac.cn.saya.laboratory.persistent.financial.service;

import ac.cn.saya.laboratory.entity.BillOfAmountEntity;
import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.financial.dao.BillDAO;
import ac.cn.saya.laboratory.tools.CurrentLineInfo;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FinancialBillService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/21 22:30
 * @Description:财政账单
 */
@Service("financialBillService")
@Transactional(value = "financialTransactionManager", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = MyException.class)
public class FinancialBillService {

    @Resource
    @Qualifier("billDAO")
    private BillDAO billDAO;

    /**
     * @描述 按天分页查询账单(若按照给定的日期未查到数据，将按照最后的交易时间作为查询条件)
     * @参数 [param] 其中时间参数 为月格式：2020-10
     * @返回值 java.util.List<ac.cn.saya.laboratory.entity.BillOfDayEntity>
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public List<BillOfDayEntity> getBillByDay(TransactionListEntity param) {
        try {
            List<BillOfDayEntity> list = billDAO.queryBillByDay(param);
            if (!list.isEmpty()) {
                return list;
            }
            // 取出最新的账单
            TransactionListEntity latestBill = billDAO.queryLatestBillByUser(param.getSource());
            if (null != latestBill && !StringUtils.isEmpty(latestBill.getTradeDate())) {
                // 用最新的账单时间进行查询
                param.setTradeDate(latestBill.getTradeDate());
                return billDAO.queryBillByDay(param);
            }
            return Collections.emptyList();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询近半年财政收支情况失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 统计指定月份的总收入和支出(若按照给定的日期未查到数据，将按照最后的交易时间作为查询条件)
     * @参数 [param]
     * @返回值 ac.cn.saya.laboratory.entity.BillOfDayEntity
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public BillOfDayEntity totalBalance(BillOfDayEntity param) {
        try {
            BillOfDayEntity balance = billDAO.totalBalance(param);
            if (null != balance) {
                return balance;
            }
            // 取出最新的账单
            TransactionListEntity latestBill = billDAO.queryLatestBillByUser(param.getSource());
            if (null != latestBill && !StringUtils.isEmpty(latestBill.getTradeDate())) {
                // 用最新的账单时间进行查询
                param.setTradeDate(latestBill.getTradeDate());
                return billDAO.totalBalance(param);
            }
            return null;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定月份的总收入和支出失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 统计指定月份中各摘要的收支情况（flag=-1）或收入（flag=1）(若按照给定的日期未查到数据，将按照最后的交易时间作为查询条件)
     * @参数 [tradeDate:月份, source:所属用户账单, flag:收支 标志]
     * @返回值 Map<String,Object>
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public Map<String,Object> totalBillByAmount(String tradeDate, String source) {
        try {
            List<BillOfAmountEntity> incomeList = billDAO.totalBillByAmount(tradeDate, source, 1);
            List<BillOfAmountEntity> payList = billDAO.totalBillByAmount(tradeDate, source, -1);
            Map<String, Object> result = new HashMap<>(4);
            if (!incomeList.isEmpty() || !payList.isEmpty()) {
                result.put("income",incomeList);
                result.put("pay",payList);
                result.put("tradeDate",tradeDate);
                return result;
            }
            // 取出最新的账单
            TransactionListEntity latestBill = billDAO.queryLatestBillByUser(source);
            if (null != latestBill && !StringUtils.isEmpty(latestBill.getTradeDate())) {
                // 用最新的账单时间进行查询
                incomeList = billDAO.totalBillByAmount((latestBill.getTradeDate()).length()>7?(latestBill.getTradeDate()).substring(0,7):"-1", source, 1);
                payList = billDAO.totalBillByAmount((latestBill.getTradeDate()).length()>7?(latestBill.getTradeDate()).substring(0,7):"-1", source, -1);
                result.put("income",incomeList);
                result.put("pay",payList);
                result.put("tradeDate",latestBill.getTradeDate());
                return result;
            }
            throw new MyException(ResultEnum.NOT_EXIST);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定月份中各摘要的收支情况失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定月份中支出（flag=-1）或收入（flag=1）的排行 (若按照给定的日期未查到数据，将按照最后的交易时间作为查询条件)
     * @参数 [tradeDate:月份, source:所属用户账单, flag:收支 标志]
     * @返回值 Map<String,Object>
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public Map<String,Object> getBillBalanceRank(String tradeDate, String source) {
        try {
            List<TransactionListEntity> incomeList = billDAO.queryBillBalanceRank(tradeDate, source, 1);
            List<TransactionListEntity> payList = billDAO.queryBillBalanceRank(tradeDate, source, -1);
            Map<String,Object> result = new HashMap<>(4);
            if (!incomeList.isEmpty() || !payList.isEmpty()) {
                result.put("income",incomeList);
                result.put("pay",payList);
                result.put("tradeDate",tradeDate);
                return result;
            }
            // 取出最新的账单
            TransactionListEntity latestBill = billDAO.queryLatestBillByUser(source);
            if (null != latestBill && !StringUtils.isEmpty(latestBill.getTradeDate())) {
                // 用最新的账单时间进行查询
                incomeList = billDAO.queryBillBalanceRank((latestBill.getTradeDate()).length()>7?(latestBill.getTradeDate()).substring(0,7):"-1", source, 1);
                payList = billDAO.queryBillBalanceRank((latestBill.getTradeDate()).length()>7?(latestBill.getTradeDate()).substring(0,7):"-1", source, -1);
                result.put("income",incomeList);
                result.put("pay",payList);
                result.put("tradeDate",latestBill.getTradeDate());
                return result;
            }
            throw new MyException(ResultEnum.NOT_EXIST);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询指定月份中支出或收入排行失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询指定月份中，某一摘要类型的收支数据
     * @参数 [param]
     * @返回值 java.util.List<ac.cn.saya.laboratory.entity.TransactionListEntity>
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public List<TransactionListEntity> getBillByAmount(TransactionListEntity param) {
        try {
            return billDAO.queryBillByAmount(param);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询指定月份中，某一摘要类型的收支数据失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * @描述 查询账单明细
     * @参数 [param]
     * @返回值 ac.cn.saya.laboratory.entity.TransactionListEntity
     * @创建人 shmily
     * @创建时间 2020/10/21
     * @修改人和其它信息
     */
    public TransactionListEntity getBillDetail(TransactionListEntity param) {
        try {
            return billDAO.queryBillDetail(param);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询账单明细失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查询指定月份的总收入、支出和总收支（硬查询）
     * @param param
     * @return
     */
    public BillOfDayEntity totalBalanceHard(BillOfDayEntity param) {
        try {
            BillOfDayEntity balance = billDAO.totalBalance(param);
            if (null != balance) {
                return balance;
            }
            return null;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定月份的总支出失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 统计指定月份中各摘要的排名
     * @param tradeDate 所在月份的日期
     * @param source 当前用户会话信息
     * @return
     */
    public List<BillOfAmountEntity> orderByAmount(String tradeDate, String source,int flag){
        try {
            return billDAO.totalBillByAmount(tradeDate, source, 1);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定月份中各摘要的排名失败", e, FinancialBillService.class);
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }
}
