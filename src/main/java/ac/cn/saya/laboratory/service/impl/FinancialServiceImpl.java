package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.TransactionReadService;
import ac.cn.saya.laboratory.service.IFinancialService;
import ac.cn.saya.laboratory.tools.Paging;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import ac.cn.saya.laboratory.tools.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: FinancialServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/4 17:13
 * @Description:
 * 财政数据提供的相关服务
 */
@Service("financialServiceImpl")
public class FinancialServiceImpl implements IFinancialService {

    private static Logger logger = LoggerFactory.getLogger(FinancialServiceImpl.class);

    @Resource
    @Qualifier("transactionReadService")
    private TransactionReadService transactionReadService;

    /**
     * 获取所有的交易类别
     *
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> getTransactionType() throws Exception {
        List<TransactionTypeEntity> list = transactionReadService.selectTransactionType();
        if (list.size() < 0)
        {
            // 没有找到交易类别
            throw new MyException(ResultEnum.NOT_EXIST);
        }
        else
        {
            return  ResultUtil.success(list);
        }
    }

    /**
     * 查看流水（这里不是明细）
     * 根据用户、类型、日期
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        Paging paging =new Paging();
        if(entity.getNowPage() == null)
        {
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null)
        {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = transactionReadService.selectTransactionCount(entity);
        if(pageSize > 0)
        {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow()-1)*paging.getPageSize(),paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionListEntity> list = transactionReadService.selectTransactionPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }
        else
        {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 查看流水子明细
     * 根据父id，本位id，flog
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        Paging paging =new Paging();
        if(entity.getNowPage() == null)
        {
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null)
        {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = transactionReadService.selectTransactionInfoCount(entity);
        if(pageSize > 0)
        {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow()-1)*paging.getPageSize(),paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionInfoEntity> list = transactionReadService.selectTransactionInfoPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }
        else
        {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 查询详细的流水明细总数
     * 根据用户、类型、日期
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> getTransactionFinal(TransactionListEntity entity, HttpServletRequest request) throws Exception {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        Paging paging =new Paging();
        if(entity.getNowPage() == null)
        {
            entity.setNowPage(1);
        }
        if(entity.getPageSize() == null)
        {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = transactionReadService.selectTransactionFinalCount(entity);
        if(pageSize > 0)
        {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow()-1)*paging.getPageSize(),paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionInfoEntity> list = transactionReadService.selectTransactionFinalPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        }
        else
        {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }
}
