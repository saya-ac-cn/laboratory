package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.BillOfAmountEntity;
import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Title: IFinancialService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/4 17:03
 * @Description: 财政数据库提供的相关服务
 */

public interface IFinancialService {

    /**
     * 按天分页查询账单
     * @param param
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getBillByDay(TransactionListEntity param,HttpServletRequest request) throws MyException;

    /**
     * 统计指定月份的总收入和支出
     * @param param
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> totalBalance(BillOfDayEntity param,HttpServletRequest request) throws MyException;

    /**
     * 统计指定月份中各摘要的收支情况
     * @param param
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> totalBillByAmount(BillOfAmountEntity param,HttpServletRequest request) throws MyException;

    /**
     * 查询指定月份中支出（flag=-1）或收入（flag=1）的排行
     * @param tradeDate 月份
     * @param request 所属用户
     * @param flag flag:收支 标志
     * @return
     * @throws MyException
     */
    public Result<Object> getBillBalanceRank(String tradeDate,HttpServletRequest request,int flag) throws MyException;

    /**
     * 查询指定月份中，某一摘要类型的收支数据
     * @param param 查询参数
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getBillByAmount(TransactionListEntity param,HttpServletRequest request) throws MyException;

    /**
     * 查询账单明细
     * @param param 查询参数
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getBillDetail(TransactionListEntity param,HttpServletRequest request) throws MyException;

    /**
     * 获取所有的交易类别
     *
     * @return
     * @throws Exception
     */
    public Result<Object> getTransactionType() throws MyException;

    /**
     * 获取所有的交易摘要
     *
     * @return
     * @throws Exception
     */
    public Result<Object> getTransactionAmount() throws MyException;

    /**
     * 查看流水（这里不是明细）
     * 根据用户、类型、日期
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 查看流水子明细
     * 根据父id，本位id，flog
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 查询详细的流水明细总数
     * 根据用户、类型、日期
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> getTransactionFinal(TransactionListEntity entity, HttpServletRequest request) throws MyException;


    /**
     * 添加财政记录父+子
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> insertTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 修改财政记录父
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> updateTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 删除财政记录父+子
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> deleteTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 添加财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> insertTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 修改财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> updateTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 删除财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> deleteTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 导出流水
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws MyException
     */
    public Result<Object> outTransactionListExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException;


    /**
     * 导出完整流水及明细
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws MyException
     */
    public Result<Object> outTransactionInfoExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException;

    /**
     * 按天统计流水
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> totalTransactionForDay(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 按月统计流水
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> totalTransactionForMonth(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 按年统计流水
     *
     * @param entity
     * @param request
     * @return
     * @throws MyException
     */
    public Result<Object> totalTransactionForYear(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 按天导出流水统计报表
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws MyException
     */
    public Result<Object> outTransactionForDayExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException;

    /**
     * 按月导出流水统计报表
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws MyException
     */
    public Result<Object> outTransactionForMonthExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException;

    /**
     * 按年导出流水统计报表
     *
     * @param entity
     * @param request
     * @param response
     * @return
     * @throws MyException
     */
    public Result<Object> outTransactionForYearExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException;

}
