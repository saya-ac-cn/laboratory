package ac.cn.saya.laboratory.controller;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.service.impl.FinancialServiceImpl;
import ac.cn.saya.laboratory.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: FinancialController
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/4 17:51
 * @Description:
 * 财政数据接口
 */

@RestController
@RequestMapping(value = "/backend/api/financial")
public class FinancialController {

    @Autowired()
    @Qualifier("financialServiceImpl")
    private FinancialServiceImpl financialService;

    /**
     * 获取财政流水（这里不是明细）
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "transaction")
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception
    {
        return financialService.getTransaction(entity,request);
    }

    /**
     * 获取所有的支付类别
     * @return
     * @throws Exception
     */
    @GetMapping(value = "transactionType")
    public Result<Object> getTransactionType() throws Exception
    {
        return financialService.getTransactionType();
    }

    /**
     * 获取支付子明细
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "transactionInfo")
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception
    {
        return financialService.getTransactionInfo(entity,request);
    }

    /**
     * 获取完整的流水明细
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "transactionFinal")
    public Result<Object> getTransactionFinal(TransactionListEntity entity, HttpServletRequest request) throws Exception
    {
        return financialService.getTransactionFinal(entity,request);
    }



}
