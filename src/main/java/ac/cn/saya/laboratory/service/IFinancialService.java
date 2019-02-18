package ac.cn.saya.laboratory.service;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.tools.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: IFinancialService
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/4 17:03
 * @Description:
 * 财政数据库提供的相关服务
 */

public interface IFinancialService {

    /**
     * 获取所有的交易类别
     * @return
     * @throws Exception
     */
    public Result<Object> getTransactionType() throws Exception;

    /**
     * 查看流水（这里不是明细）
     * 根据用户、类型、日期
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception;

    /**
     * 查看流水子明细
     * 根据父id，本位id，flog
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception;

    /**
     * 查询详细的流水明细总数
     * 根据用户、类型、日期
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    public Result<Object> getTransactionFinal(TransactionListEntity entity, HttpServletRequest request) throws Exception;



}
