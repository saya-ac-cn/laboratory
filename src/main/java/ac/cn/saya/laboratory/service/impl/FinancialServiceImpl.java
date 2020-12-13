package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.financial.service.FinancialBillService;
import ac.cn.saya.laboratory.persistent.financial.service.FinancialDeclareService;
import ac.cn.saya.laboratory.service.IFinancialService;
import ac.cn.saya.laboratory.tools.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: FinancialServiceImpl
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/4 17:13
 * @Description: 财政数据提供的相关服务
 */
@Service("financialServiceImpl")
public class FinancialServiceImpl implements IFinancialService {


    @Resource
    @Qualifier("recordService")
    private RecordService recordService;

    @Resource
    @Qualifier("financialDeclareService")
    private FinancialDeclareService financialDeclareService;

    @Resource
    @Qualifier("financialBillService")
    private FinancialBillService financialBillService;

    /**
     * @描述 按天分页查询账单
     * @参数  [param] 其中时间参数 为月格式：2020-10
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息 无
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getBillByDay(TransactionListEntity param,HttpServletRequest request){
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        param.setSource(userSession.getUser());
        List<BillOfDayEntity> billByDayList = financialBillService.getBillByDay(param);
        if (billByDayList == null || billByDayList.isEmpty()) {
            // 没有找到账单
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(billByDayList);
        }
    }

    /**
     * @描述 统计指定月份的总收入和支出
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> totalBalance(BillOfDayEntity param,HttpServletRequest request) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        param.setSource(userSession.getUser());
        BillOfDayEntity balance = financialBillService.totalBalance(param);
        if (null == balance){
            throw new MyException(ResultEnum.NOT_EXIST);
        }else {
            return ResultUtil.success(balance);
        }
    }

    /**
     * @描述 统计指定月份中各摘要的收支情况（flag=-1）或收入（flag=1）
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> totalBillByAmount(String tradeDate,HttpServletRequest request,int flag) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        List<BillOfAmountEntity> list = financialBillService.totalBillByAmount(tradeDate, userSession.getUser(), flag);
        if (list == null || list.isEmpty()) {
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(list);
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
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getBillBalanceRank(String tradeDate,HttpServletRequest request,int flag) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        List<TransactionListEntity> balanceRank = financialBillService.getBillBalanceRank(tradeDate, userSession.getUser(), flag);
        if (balanceRank == null || balanceRank.isEmpty()) {
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(balanceRank);
        }
    }

    /**
     * @描述 查询指定月份中，某一摘要类型的收支数据
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getBillByAmount(TransactionListEntity param,HttpServletRequest request) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        param.setSource(userSession.getUser());
        List<TransactionListEntity> list = financialBillService.getBillByAmount(param);
        if (list == null || list.isEmpty()) {
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(list);
        }
    }

    /**
     * @描述 查询账单明细
     * @参数  [param]
     * @返回值  ac.cn.saya.laboratory.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/10/21
     * @修改人和其它信息
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getBillDetail(TransactionListEntity param,HttpServletRequest request) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        param.setSource(userSession.getUser());
        TransactionListEntity detail = financialBillService.getBillDetail(param);
        if (null == detail){
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(detail);
        }
    }

    /**
     * 获取所有的交易类别
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getTransactionType() throws MyException {
        List<TransactionTypeEntity> list = financialDeclareService.selectTransactionType();
        if (list == null || list.isEmpty()) {
            // 没有找到交易类别
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(list);
        }
    }

    /**
     * 获取所有的交易摘要
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getTransactionAmount() throws MyException {
        List<TransactionAmountEntity> list = financialDeclareService.selectTransactionAmount();
        if (list == null || list.isEmpty()) {
            // 没有找到交易摘要
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(list);
        }
    }

    /**
     * 查看流水（这里不是明细）
     * 根据用户、类型、日期
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 财政流水记录
     */
    @Override
    public Result<Object> getTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionListEntity> list = financialDeclareService.selectTransactionPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 查看流水子明细
     * 根据父id，本位id，flog
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 支付详情
     */
    @Override
    public Result<Object> getTransactionInfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionInfoCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionInfoEntity> list = financialDeclareService.selectTransactionInfoPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 查询详细的流水明细总数
     * 根据用户、类型、日期
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return 完整的流水详情
     */
    @Override
    public Result<Object> getTransactionFinal(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionFinalCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionInfoEntity> list = financialDeclareService.selectTransactionFinalPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }


    /**
     * 添加财政记录父+子
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> insertTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        Result<Object> result = financialDeclareService.insertTransaction(entity);
        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
            recordService.record("OX025", request);
        }
        return result;
    }

    /**
     * 修改财政记录父
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> updateTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        // 只允许修改交易类别以及交易摘要
        if (entity == null || entity.getTradeId() == null || entity.getTradeType() == null || StringUtils.isEmpty(entity.getTransactionAmount())) {
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            entity.setSource(userSession.getUser());
            Result<Object> result = financialDeclareService.updateTransaction(entity);
            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                recordService.record("OX026", request);
            }
            return result;
        }
    }

    /**
     * 这里是级联删除
     * 删除财政记录父+子
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> deleteTransaction(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        if (entity == null || entity.getTradeId() == null) {
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            Result<Object> result = financialDeclareService.deleteTransaction(entity, userSession.getUser());
            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                //删除流水
                recordService.record("OX027", request);
            }
            return result;
        }
    }

    /**
     * 单行
     * 添加财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> insertTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Result<Object> result = financialDeclareService.insertTransactioninfo(entity, userSession.getUser());
        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
            //删除流水
            recordService.record("OX028", request);
        }
        return result;
    }

    /**
     * 单行
     * 修改财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> updateTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Result<Object> result = financialDeclareService.updateTransactioninfo(entity, userSession.getUser());
        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
            recordService.record("OX029", request);
        }
        return result;
    }

    /**
     * 删除财政子记录
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 处理结果
     */
    @Override
    public Result<Object> deleteTransactioninfo(TransactionInfoEntity entity, HttpServletRequest request) throws MyException {
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Result<Object> result = null;
        try {
            result = financialDeclareService.deleteTransactioninfo(entity, userSession.getUser());
        } catch (Exception e) {
            throw new MyException(ResultEnum.ERROP);
        }
        if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
            recordService.record("OX030", request);
        }
        return result;
    }

    /**
     * 导出流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @Override
    public Result<Object> outTransactionListExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        String[] keys = {"tradeId", "deposited", "expenditure", "transactionType", "currencyNumber", "tradeDate", "transactionAmount", "createTime", "updateTime"};
        //放置到第一行的字段名
        String[] titles = {"流水号", "存入", "取出", "交易方式", "产生总额", "产生日期", "摘要", "创建时间", "修改时间"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionCount(entity);
            if (pageSize <= 0) {
                response.addHeader("status","404");
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            entity.setSource(userSession.getUser());
            //获取满足条件的记录集合
            List<TransactionListEntity> entityList = financialDeclareService.selectTransactionPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (TransactionListEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("tradeId", item.getTradeId());
                json.put("deposited", item.getDeposited());
                json.put("expenditure", item.getExpenditure());
                json.put("transactionType", item.getTradeTypeEntity().getTransactionType());
                json.put("currencyNumber", item.getCurrencyNumber());
                json.put("tradeDate", item.getTradeDate());
                json.put("transactionAmount", item.getTradeAmountEntity().getTag());
                json.put("createTime", item.getCreateTime());
                json.put("updateTime", item.getUpdateTime());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("财务流水.xlsx", "UTF-8"));
            response.addHeader("status","200");
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("status","500");
        }

        return null;
    }

    /**
     * 导出完整流水及明细
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @Override
    public Result<Object> outTransactionInfoExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        String[] keys = {"tradeId", "deposited", "expenditure", "transactionType", "currencyNumber", "tradeDate", "transactionAmount", "flog", "itemNumber", "currencyDetails", "createTime", "updateTime"};
        //放置到第一行的字段名
        String[] titles = {"流水号", "存入", "取出", "交易方式", "产生总额", "产生日期", "摘要", "标志", "金额", "详情", "创建时间", "修改时间"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionFinalCount(entity);
            if (pageSize <= 0) {
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            entity.setSource(userSession.getUser());
            //获取满足条件的记录集合
            List<TransactionInfoEntity> entityList = financialDeclareService.selectTransactionFinalPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (TransactionInfoEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("tradeId", item.getTransactionListEntity().getTradeId());
                json.put("deposited", item.getTransactionListEntity().getDeposited());
                json.put("expenditure", item.getTransactionListEntity().getExpenditure());
                json.put("transactionType", item.getTransactionListEntity().getTradeTypeEntity().getTransactionType());
                json.put("currencyNumber", item.getTransactionListEntity().getCurrencyNumber());
                json.put("tradeDate", item.getTransactionListEntity().getTradeDate());
                json.put("transactionAmount", item.getTransactionListEntity().getTradeAmountEntity().getTag());
                if (item.getFlog() == 1) {
                    json.put("flog", "存入");
                } else {
                    json.put("flog", "取出");
                }
                json.put("itemNumber", item.getCurrencyNumber());
                json.put("currencyDetails", item.getCurrencyDetails());
                json.put("createTime", item.getTransactionListEntity().getCreateTime());
                json.put("updateTime", item.getTransactionListEntity().getUpdateTime());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("财务流水明细.xlsx", "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按天统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @Override
    public Result<Object> totalTransactionForDay(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        entity.setSource(userSession.getUser());
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionForDayCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionListEntity> list = financialDeclareService.selectTransactionForDayPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 按月统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @Override
    public Result<Object> totalTransactionForMonth(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        entity.setSource(userSession.getUser());
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionForMonthCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionListEntity> list = financialDeclareService.selectTransactionForMonthPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 按年统计流水
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @return 统计结果
     */
    @Override
    public Result<Object> totalTransactionForYear(TransactionListEntity entity, HttpServletRequest request) throws MyException {
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        Paging paging = new Paging();
        if (entity.getNowPage() == null) {
            entity.setNowPage(1);
        }
        if (entity.getPageSize() == null) {
            entity.setPageSize(20);
        }
        entity.setSource(userSession.getUser());
        //每页显示记录的数量
        paging.setPageSize(entity.getPageSize());
        //获取满足条件的总记录（不分页）
        Long pageSize = financialDeclareService.selectTransactionForYearCount(entity);
        if (pageSize > 0) {
            //总记录数
            paging.setDateSum(pageSize);
            //计算总页数
            paging.setTotalPage();
            //设置当前的页码-并校验是否超出页码范围
            paging.setPageNow(entity.getNowPage());
            //设置行索引
            entity.setPage((paging.getPageNow() - 1) * paging.getPageSize(), paging.getPageSize());
            //获取满足条件的记录集合
            List<TransactionListEntity> list = financialDeclareService.selectTransactionForYearPage(entity);
            paging.setGrid(list);
            return ResultUtil.success(paging);
        } else {
            //未找到有效记录
            throw new MyException(ResultEnum.NOT_EXIST);
        }
    }

    /**
     * 按天导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @Override
    public Result<Object> outTransactionForDayExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        String[] keys = {"tradeDate", "deposited", "expenditure", "currencyNumber"};
        //放置到第一行的字段名
        String[] titles = {"产生日期", "流入", "流出", "产生总额"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionForDayCount(entity);
            if (pageSize <= 0) {
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            //获取满足条件的记录集合
            List<TransactionListEntity> entityList = financialDeclareService.selectTransactionForDayPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (TransactionListEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("tradeDate", item.getTradeDate());
                json.put("deposited", item.getDeposited());
                json.put("expenditure", item.getExpenditure());
                json.put("currencyNumber", item.getCurrencyNumber());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("日度财务明细.xlsx", "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按月导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @Override
    public Result<Object> outTransactionForMonthExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        String[] keys = {"tradeDate", "deposited", "expenditure", "currencyNumber"};
        //放置到第一行的字段名
        String[] titles = {"产生日期", "流入", "流出", "产生总额"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionForMonthCount(entity);
            if (pageSize <= 0) {
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            //获取满足条件的记录集合
            List<TransactionListEntity> entityList = financialDeclareService.selectTransactionForMonthPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (TransactionListEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("tradeDate", item.getTradeDate());
                json.put("deposited", item.getDeposited());
                json.put("expenditure", item.getExpenditure());
                json.put("currencyNumber", item.getCurrencyNumber());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("月度财务明细.xlsx", "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按年导出流水统计报表
     *
     * @param entity 表单参数
     * @param request 用户会话请求
     * @param response 响应的会话参数
     * @return 文件流
     */
    @Override
    public Result<Object> outTransactionForYearExcel(TransactionListEntity entity, HttpServletRequest request, HttpServletResponse response) throws MyException {
        String[] keys = {"tradeDate", "deposited", "expenditure", "currencyNumber"};
        //放置到第一行的字段名
        String[] titles = {"产生日期", "流入", "流出", "产生总额"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionForYearCount(entity);
            if (pageSize <= 0) {
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            //获取满足条件的记录集合
            List<TransactionListEntity> entityList = financialDeclareService.selectTransactionForYearPage(entity);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (TransactionListEntity item : entityList) {
                JSONObject json = new JSONObject();
                json.put("deposited", item.getDeposited());
                json.put("expenditure", item.getExpenditure());
                json.put("currencyNumber", item.getCurrencyNumber());
                json.put("tradeDate", item.getTradeDate());
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("年度财务明细.xlsx", "UTF-8"));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplateSimple(keys, titles, jsonObjectList, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
