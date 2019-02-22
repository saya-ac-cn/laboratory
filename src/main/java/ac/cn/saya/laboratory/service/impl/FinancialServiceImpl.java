package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.service.TransactionReadService;
import ac.cn.saya.laboratory.persistent.service.TransactionWriteService;
import ac.cn.saya.laboratory.service.IFinancialService;
import ac.cn.saya.laboratory.tools.Paging;
import ac.cn.saya.laboratory.tools.Result;
import ac.cn.saya.laboratory.tools.ResultEnum;
import ac.cn.saya.laboratory.tools.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Qualifier("recordService")//日志助手表
    private RecordService recordService;

    @Resource
    @Qualifier("transactionReadService")
    private TransactionReadService transactionReadService;

    @Resource
    @Qualifier("transactionWriteService")
    private TransactionWriteService transactionWriteService;

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


    /**
     * 添加财政记录父+子
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> insertTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception {
        //第一步遍历所有的财政字表，求得总和
        List<TransactionInfoEntity> requestInfoList = entity.getInfoList();
        // 存入总金额
        Double saveMoney = 0.0;
        // 支取总金额
        Double takeMoney = 0.0;
        // 当日发生总金额（存入+支取）
        Double happenMoney = 0.0;
        for(TransactionInfoEntity item : requestInfoList){
            if(item.getFlog() == 1){
                // 存入
                saveMoney += item.getCurrencyNumber();
                continue;
            }
            if(item.getFlog() == 2){
                // 支取
                takeMoney += item.getCurrencyNumber();
                continue;
            }
        }
        happenMoney = saveMoney + takeMoney;
        // 设置存入总金额
        entity.setDeposited(saveMoney);
        // 设置支取总金额
        entity.setExpenditure(takeMoney);
        // 设置交易总金额
        entity.setCurrencyNumber(happenMoney);
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        String userSession = (String) request.getSession().getAttribute("user");
        entity.setSource(userSession);
        // 开始写入父表的数据
        Integer backfillValue = transactionWriteService.insertTransactionList(entity);
        if(backfillValue > 0){
            // 父表操作成功
            // 开始写入子表的数据
            for(TransactionInfoEntity item : requestInfoList){
                // 设置流水号，也就是在父表写入成功后，主键回填的值
                item.setTradeId(backfillValue);
                transactionWriteService.insertTransactionInfo(item);
            }
            /**
             * 申报流水
             */
            recordService.record("OX025",request);
            return ResultUtil.success();
        }else{
            logger.warn("在写入到财政父表异常");
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 修改财政记录父
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> updateTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception {
        // 只允许修改交易类别以及交易摘要
        if(entity == null || entity.getTradeId() == null || entity.getTradeType() == null || StringUtils.isEmpty(entity.getTransactionAmount())){
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }else{
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            entity.setSource(userSession);
            if(transactionWriteService.updateTransactionList(entity)>0){
                /**
                 * 修改流水
                 */
                recordService.record("OX026",request);
                return ResultUtil.success();
            }else{
                return ResultUtil.error(-1,"修改失败");
            }
        }
    }

    /**
     * 这里是级联删除
     * 删除财政记录父+子
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> deleteTransaction(TransactionListEntity entity, HttpServletRequest request) throws Exception {
        if(entity == null || entity.getTradeId() == null){
            throw new MyException(ResultEnum.NOT_PARAMETER);
        }else{
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            String userSession = (String) request.getSession().getAttribute("user");
            if(transactionWriteService.deleteTransactionList(entity.getTradeId(),userSession) > 0){
                /**
                 * 删除流水
                 */
                recordService.record("OX027",request);
                return ResultUtil.success();
            }else{
                return ResultUtil.error(-1,"删除失败");
            }
        }
    }

    /**
     * 单行
     * 添加财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> insertTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception {
        // 先把子记录进行写入
        if(transactionWriteService.insertTransactionInfo(entity) > 0){
            // 查询本流水号下的所有子记录
            TransactionInfoEntity queryEntity = new TransactionInfoEntity();
            queryEntity.setTradeId(entity.getTradeId());
            // 财政子表记录总行数
            Long itemConut = transactionReadService.selectTransactionInfoCount(queryEntity);
            if(itemConut > 0){
                queryEntity.setStartLine(0);
                queryEntity.setEndLine(itemConut.intValue());
                List<TransactionInfoEntity> itemList = transactionReadService.selectTransactionInfoPage(queryEntity);
                // 存入总金额
                Double saveMoney = 0.0;
                // 支取总金额
                Double takeMoney = 0.0;
                // 当日发生总金额（存入+支取）
                Double happenMoney = 0.0;
                TransactionListEntity queryParentEntity = new TransactionListEntity();
                // 根据主键进行查询，最多只能有一条
                queryParentEntity.setTradeId(entity.getTradeId());
                queryParentEntity.setStartLine(0);
                queryParentEntity.setEndLine(1);
                List<TransactionListEntity> transactionPage = transactionReadService.selectTransactionPage(queryParentEntity);
                if(transactionPage.size() > 0){
                    TransactionListEntity writeEntity = transactionPage.get(0);
                    for(TransactionInfoEntity item : itemList){
                        if(item.getFlog() == 1){
                            // 存入
                            saveMoney += item.getCurrencyNumber();
                            continue;
                        }
                        if(item.getFlog() == 2){
                            // 支取
                            takeMoney += item.getCurrencyNumber();
                            continue;
                        }
                    }
                    happenMoney = saveMoney + takeMoney;
                    // 设置存入总金额
                    writeEntity.setDeposited(saveMoney);
                    // 设置支取总金额
                    writeEntity.setExpenditure(takeMoney);
                    // 设置交易总金额
                    writeEntity.setCurrencyNumber(happenMoney);
                    //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                    String userSession = (String) request.getSession().getAttribute("user");
                    writeEntity.setSource(userSession);
                    // 开始写入父表的数据
                    if(transactionWriteService.updateTransactionList(writeEntity) > 0){
                        /**
                         * 补充流水明细
                         */
                        recordService.record("OX028",request);
                        return ResultUtil.success();
                    }else
                    {
                        throw new MyException(ResultEnum.RollBACK);
                    }
                }else{
                    return ResultUtil.error(-1,"财政父表数据丢失");
                }
            }else{
                logger.warn("写入财政字表失败");
                throw new MyException(ResultEnum.ERROP);
            }
        }else{
            logger.warn("写入财政字表失败");
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 单行
     * 修改财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> updateTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception {
        // 先把子记录进行写入
        if(transactionWriteService.updateTransactionInfo(entity) > 0){
            // 查询本流水号下的所有子记录
            TransactionInfoEntity queryEntity = new TransactionInfoEntity();
            queryEntity.setTradeId(entity.getTradeId());
            // 财政子表记录总行数
            Long itemConut = transactionReadService.selectTransactionInfoCount(queryEntity);
            if(itemConut > 0){
                queryEntity.setStartLine(0);
                queryEntity.setEndLine(itemConut.intValue());
                List<TransactionInfoEntity> itemList = transactionReadService.selectTransactionInfoPage(queryEntity);
                // 存入总金额
                Double saveMoney = 0.0;
                // 支取总金额
                Double takeMoney = 0.0;
                // 当日发生总金额（存入+支取）
                Double happenMoney = 0.0;
                TransactionListEntity queryParentEntity = new TransactionListEntity();
                // 根据主键进行查询，最多只能有一条
                queryParentEntity.setTradeId(entity.getTradeId());
                queryParentEntity.setStartLine(0);
                queryParentEntity.setEndLine(1);
                List<TransactionListEntity> transactionPage = transactionReadService.selectTransactionPage(queryParentEntity);
                if(transactionPage.size() > 0){
                    TransactionListEntity writeEntity = transactionPage.get(0);
                    for(TransactionInfoEntity item : itemList){
                        if(item.getFlog() == 1){
                            // 存入
                            saveMoney += item.getCurrencyNumber();
                            continue;
                        }
                        if(item.getFlog() == 2){
                            // 支取
                            takeMoney += item.getCurrencyNumber();
                            continue;
                        }
                    }
                    happenMoney = saveMoney + takeMoney;
                    // 设置存入总金额
                    writeEntity.setDeposited(saveMoney);
                    // 设置支取总金额
                    writeEntity.setExpenditure(takeMoney);
                    // 设置交易总金额
                    writeEntity.setCurrencyNumber(happenMoney);
                    //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                    String userSession = (String) request.getSession().getAttribute("user");
                    writeEntity.setSource(userSession);
                    // 开始写入父表的数据
                    if(transactionWriteService.updateTransactionList(writeEntity) > 0){
                        /**
                         * 修改流水明细
                         */
                        recordService.record("OX029",request);
                        return ResultUtil.success();
                    }else
                    {
                        throw new MyException(ResultEnum.RollBACK);
                    }
                }else{
                    return ResultUtil.error(-1,"财政父表数据丢失");
                }
            }else{
                logger.warn("修改财政子表失败");
                throw new MyException(ResultEnum.ERROP);
            }
        }else{
            logger.warn("修改财政子表失败");
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 删除财政子记录
     *
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Result<Object> deleteTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws Exception {
        // 查询本流水号下的所有子记录
        TransactionInfoEntity queryEntity = new TransactionInfoEntity();
        queryEntity.setTradeId(entity.getTradeId());
        // 财政子表记录总行数
        Long itemConut = transactionReadService.selectTransactionInfoCount(queryEntity);
        if(itemConut > 0){
            String userSession = (String) request.getSession().getAttribute("user");
            if(itemConut == 1 || itemConut.equals(1)){
                // 只有一条记录时，直接调用父表的方法级联删除即可
                if(transactionWriteService.deleteTransactionList(entity.getTradeId(),userSession) > 0){
                    /**
                     * 删除流水明细
                     */
                    recordService.record("OX030",request);
                    return ResultUtil.success();
                }else{
                    return ResultUtil.error(-1,"删除失败");
                }
            }else{
                // 先删除子表，然后修改父表的值
                if(transactionWriteService.deleteTransactionInfo(entity.getId(),userSession) > 0 ){
                    // 查询本流水号下的所有子记录
                    // 财政子表记录总行数
                    itemConut = transactionReadService.selectTransactionInfoCount(queryEntity);
                    if(itemConut > 0){
                        queryEntity.setStartLine(0);
                        queryEntity.setEndLine(itemConut.intValue());
                        List<TransactionInfoEntity> itemList = transactionReadService.selectTransactionInfoPage(queryEntity);
                        // 存入总金额
                        Double saveMoney = 0.0;
                        // 支取总金额
                        Double takeMoney = 0.0;
                        // 当日发生总金额（存入+支取）
                        Double happenMoney = 0.0;
                        TransactionListEntity queryParentEntity = new TransactionListEntity();
                        // 根据主键进行查询，最多只能有一条
                        queryParentEntity.setTradeId(entity.getTradeId());
                        queryParentEntity.setStartLine(0);
                        queryParentEntity.setEndLine(1);
                        List<TransactionListEntity> transactionPage = transactionReadService.selectTransactionPage(queryParentEntity);
                        if(transactionPage.size() > 0){
                            TransactionListEntity writeEntity = transactionPage.get(0);
                            for(TransactionInfoEntity item : itemList){
                                if(item.getFlog() == 1){
                                    // 存入
                                    saveMoney += item.getCurrencyNumber();
                                    continue;
                                }
                                if(item.getFlog() == 2){
                                    // 支取
                                    takeMoney += item.getCurrencyNumber();
                                    continue;
                                }
                            }
                            happenMoney = saveMoney + takeMoney;
                            // 设置存入总金额
                            writeEntity.setDeposited(saveMoney);
                            // 设置支取总金额
                            writeEntity.setExpenditure(takeMoney);
                            // 设置交易总金额
                            writeEntity.setCurrencyNumber(happenMoney);
                            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                            writeEntity.setSource(userSession);
                            // 开始写入父表的数据
                            if(transactionWriteService.updateTransactionList(writeEntity) > 0){
                                /**
                                 * 删除流水明细
                                 */
                                recordService.record("OX030",request);
                                return ResultUtil.success();
                            }else
                            {
                                throw new MyException(ResultEnum.RollBACK);
                            }
                        }else{
                            return ResultUtil.error(-1,"财政父表数据丢失");
                        }
                    }else{
                        logger.warn("修改财政子表失败");
                        throw new MyException(ResultEnum.ERROP);
                    }
                }else{
                    return ResultUtil.error(-1,"删除失败");
                }
            }
        }else{
            return ResultUtil.error(-1,"记录已经不存在");
        }
    }
}
