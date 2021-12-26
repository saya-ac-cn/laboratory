package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.service.impl.FinancialServiceImpl;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: FinancialController
 * @ProjectName DataCenter
 * @Author Saya
 * @Date: 2018/11/4 17:51
 * @Description: 财政数据接口
 */

@RestController
@RequestMapping(value = "/backend/api/financial")
public class FinancialController {
    
    private FinancialServiceImpl financialService;

    @Autowired
    @Qualifier("financialServiceImpl")
    public void setFinancialService(FinancialServiceImpl financialService) {
        this.financialService = financialService;
    }

    /**
     * 获取财政流水（这里不是明细）
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 财政流水记录
     */
    @GetMapping(value = "transaction")
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.getTransaction(entity, request);
    }

    /**
     * 获取所有的支付类别
     *
     * @return 支付类型
     */
    @GetMapping(value = "transactionType")
    public Result<Object> getTransactionType() throws MyException {
        return financialService.getTransactionType();
    }

    /**
     * 获取所有的交易摘要
     *
     * @return 摘要类别
     */
    @GetMapping(value = "transactionAmount")
    public Result<Object> getTransactionAmount() throws MyException {
        return financialService.getTransactionAmount();
    }

    /**
     * 获取支付子明细
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 支付详情
     */
    @GetMapping(value = "transactionInfo")
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        return financialService.getTransactionInfo(entity, request);
    }

    /**
     * 查看收支明细（明细记录折叠存）
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 完整的流水详情
     */
    @GetMapping(value = "transactionDetail")
    public Result<TransactionListEntity> getTransactionDetail(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.getTransactionDetail(entity, request);
    }

    /**
     * 分页查看收支明细（明细记录折叠存）
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 完整的流水详情
     */
    @GetMapping(value = "transactionDetailPage")
    public Result<Object> getTransactionDetailPage(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.getTransactionDetailPage(entity, request);
    }

    /**
     * 添加财政记录父+子
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @PostMapping(value = "insertTransaction")
    public Result<Object> insertTransaction(@RequestBody TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.insertTransaction(entity, request);
    }

    /**
     * 修改财政记录父
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @PutMapping(value = "updateTransaction")
    public Result<Object> updateTransaction(@RequestBody TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.updateTransaction(entity, request);
    }

    /**
     * 删除财政记录父+子
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @DeleteMapping(value = "deleteTransaction")
    public Result<Object> deleteTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.deleteTransaction(entity, request);
    }

    /**
     * 添加财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @PostMapping(value = "insertTransactioninfo")
    public Result<Object> insertTransactioninfo(@RequestBody TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        return financialService.insertTransactioninfo(entity, request);
    }

    /**
     * 修改财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @PutMapping(value = "updateTransactioninfo")
    public Result<Object> updateTransactioninfo(@RequestBody TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        return financialService.updateTransactioninfo(entity, request);
    }

    /**
     * 删除财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @DeleteMapping(value = "deleteTransactioninfo")
    public Result<Object> deleteTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        return financialService.deleteTransactioninfo(entity, request);
    }

    /**
     * 导出流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @GetMapping(value = "outTransactionListExcel")
    public Result<Object> outTransactionListExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        return financialService.outTransactionListExcel(entity, request, response);
    }


    /**
     * 导出完整流水及明细
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @GetMapping(value = "outTransactionInfoExcel")
    public Result<Object> outTransactionInfoExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        return financialService.outTransactionInfoExcel(entity, request, response);
    }

    /**
     * 按天统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @GetMapping(value = "totalTransactionForDay")
    public Result<Object> totalTransactionForDay(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.totalTransactionForDay(entity, request);
    }

    /**
     * 按月统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @GetMapping(value = "totalTransactionForMonth")
    public Result<Object> totalTransactionForMonth(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.totalTransactionForMonth(entity, request);
    }

    /**
     * 按年统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @GetMapping(value = "totalTransactionForYear")
    public Result<Object> totalTransactionForYear(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        return financialService.totalTransactionForYear(entity, request);
    }

    /**
     * 按天导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @GetMapping(value = "outTransactionForDayExcel")
    public Result<Object> outTransactionForDayExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        return financialService.outTransactionForDayExcel(entity, request, response);
    }

    /**
     * 按月导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @GetMapping(value = "outTransactionForMonthExcel")
    public Result<Object> outTransactionForMonthExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        return financialService.outTransactionForMonthExcel(entity, request, response);
    }

    /**
     * 按年导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @GetMapping(value = "outTransactionForYearExcel")
    public Result<Object> outTransactionForYearExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        return financialService.outTransactionForYearExcel(entity, request, response);
    }

    /**
     * 收支增长率
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 本月总收支，日均收支，环比增长，同比增长
     */
    @GetMapping(value = "accountGrowthRate/{tradeDate}")
    public Result<Object> accountGrowthRate(@PathVariable("tradeDate") String tradeDate,HttpServletRequest request){
        return financialService.accountGrowthRate(tradeDate,request);
    }

    /**
     * 收入比重
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 收入率 总收支
     */
    @GetMapping(value = "incomePercentage/{tradeDate}")
    public Result<Object> incomePercentage(@PathVariable("tradeDate") String tradeDate,HttpServletRequest request){
        return financialService.incomePercentage(tradeDate,request);
    }

    /**
     * 统计指定月份中各摘要的排名
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    @GetMapping(value = "orderByAmount/{tradeDate}")
    public Result<Object> orderByAmount(@PathVariable("tradeDate") String tradeDate,HttpServletRequest request){
        return financialService.orderByAmount(tradeDate,request);
    }

    /**
     * 统计指定指定日期月份前6个月的账单
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    @GetMapping(value = "preSixMonthBill/{tradeDate}")
    public Result<Object> preSixMonthBill(@PathVariable("tradeDate") String tradeDate,HttpServletRequest request){
        return financialService.preSixMonthBill(tradeDate, request);
    }

}