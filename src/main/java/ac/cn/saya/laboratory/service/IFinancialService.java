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
     * 查看收支明细（明细记录折叠存）
     * 根据用户、类型、摘要、日期
     *
     * @param entity
     * @param request
     * @return
     */
    public Result<TransactionListEntity> getTransactionDetail(TransactionListEntity entity, HttpServletRequest request) throws MyException;

    /**
     * 分页查看收支明细（明细记录折叠存）
     * 根据用户、类型、摘要、日期
     *
     * @param entity
     * @param request
     * @return
     */
    public Result<Object> getTransactionDetailPage(TransactionListEntity entity, HttpServletRequest request) throws MyException;


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

    /**
     * 收支增长率
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 本月总收支，日均收支，环比增长，同比增长
     */
    public Result<Object> accountGrowthRate(String tradeDate,HttpServletRequest request);

    /**
     * 收入比重
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 收入率 总收支
     */
    public Result<Object> incomePercentage(String tradeDate,HttpServletRequest request);

    /**
     * 统计指定月份中各摘要的排名
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    public Result<Object> orderByAmount(String tradeDate,HttpServletRequest request);

    /**
     * 统计指定指定日期月份前6个月的账单
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    public Result<Object> preSixMonthBill(String tradeDate,HttpServletRequest request);

}
