package ac.cn.saya.laboratory.service.impl;

import ac.cn.saya.laboratory.entity.*;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.financial.service.FinancialDeclareService;
import ac.cn.saya.laboratory.service.IFinancialService;
import ac.cn.saya.laboratory.tools.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取所有的交易类别
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = MyException.class)
    public Result<Object> getTransactionType() throws MyException {
        List<TransactionTypeEntity> list = financialDeclareService.selectTransactionType();
        if (CollectionUtils.isEmpty(list)) {
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
        if (CollectionUtils.isEmpty(list)) {
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
     * 查看收支明细（明细记录折叠存）
     * 根据用户、类型、摘要、日期
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return
     */
    @Override
    public Result<TransactionListEntity> getTransactionDetail(TransactionListEntity entity, HttpServletRequest request) throws MyException{
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        entity.setSource(userSession.getUser());
        TransactionListEntity detail = financialDeclareService.selectTransactionDetail(entity);
        if (null == detail){
            throw new MyException(ResultEnum.NOT_EXIST);
        } else {
            return ResultUtil.success(detail);
        }
    }

    /**
     * 分页查看收支明细（明细记录折叠存）
     * 根据用户、类型、摘要、日期
     *
     * @param entity 查询参数
     * @param request 用户会话请求
     * @return
     */
    @Override
    public Result<Object> getTransactionDetailPage(TransactionListEntity entity, HttpServletRequest request) throws MyException{
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
        Long pageSize = financialDeclareService.selectTransactionDetailCount(entity);
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
            List<TransactionListEntity> list = financialDeclareService.selectTransactionDetailPage(entity);
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
        String[] titles = {"流水号", "收入金额", "支出金额", "交易方式", "收支总额", "交易时间", "摘要", "填报时间", "修改时间"};
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
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (TransactionListEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
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
        String[] rootKeys = {"tradeId", "deposited", "expenditure","billNumber","tradeType","tradeDate","source","transactionAmount","createTime", "updateTime"};
        String[] childKeys = {"infoList","currencyDetails","flog", "currencyNumber"};
        //放置到第一行的字段名
        String[] titles = {"流水号", "存入金额", "取出金额",  "收支总额","交易方式", "交易时间","用户", "摘要", "填报时间", "修改时间","交易说明", "交易类型",  "交易金额"};
        //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
        UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
        try {
            //获取满足条件的总记录（不分页）
            Long pageSize = financialDeclareService.selectTransactionDetailCount(entity);
            if (pageSize <= 0) {
                return ResultUtil.error(-1, "没有可导出的数据");
            }
            //设置行索引
            entity.setPage(0, pageSize.intValue());
            entity.setSource(userSession.getUser());
            //获取满足条件的记录集合
            List<TransactionListEntity> entityList = financialDeclareService.selectTransactionDetailPage(entity);
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (TransactionListEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
                json.put("tradeId", item.getTradeId());
                json.put("deposited", item.getDeposited());
                json.put("expenditure", item.getExpenditure());
                json.put("billNumber",item.getCurrencyNumber());
                json.put("tradeType", item.getTradeTypeEntity().getTransactionType());
                json.put("tradeDate", item.getTradeDate());
                json.put("source",item.getSource());
                json.put("transactionAmount", item.getTradeAmountEntity().getTag());
                json.put("createTime", item.getCreateTime());
                json.put("updateTime", item.getUpdateTime());
                List<TransactionInfoEntity> infoList = item.getInfoList();
                if (null != infoList && !infoList.isEmpty()){
                    ArrayNode infoJson = JackJsonUtil.createArrayNode();
                    for (TransactionInfoEntity info:infoList) {
                        ObjectNode childJson = JackJsonUtil.createObjectNode();;
                        childJson.put("currencyDetails", info.getCurrencyDetails());
                        if (info.getFlog() == 1) {
                            childJson.put("flog", "收入");
                        } else {
                            childJson.put("flog", "支出");
                        }
                        childJson.put("currencyNumber", info.getCurrencyNumber());
                        infoJson.add(childJson);
                    }
                    json.put(childKeys[0],infoJson);
                }
                jsonObjectList.add(json);
            }
            // 设置contentType为excel格式
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            //设置文件头：最后一个参数是设置下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("财务流水明细.xlsx", StandardCharsets.UTF_8));
            ServletOutputStream out = response.getOutputStream();
            response.flushBuffer();
            OutExcelUtils.outExcelTemplatePlus(rootKeys, childKeys,titles, jsonObjectList, out);
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
        String[] titles = {"交易时间", "收入金额", "支出金额", "收支总额"};
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
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (TransactionListEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
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
        String[] titles = {"交易时间", "收入金额", "支出金额", "收支总额"};
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
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (TransactionListEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
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
        String[] titles = {"交易时间", "收入金额", "支出金额", "收支总额"};
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
            ArrayNode jsonObjectList = JackJsonUtil.createArrayNode();
            for (TransactionListEntity item : entityList) {
                ObjectNode json = JackJsonUtil.createObjectNode();
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

    /**
     * 收支增长率
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 本月总收支，日均收支，环比增长，同比增长
     */
    @Override
    public Result<Object> accountGrowthRate(String tradeDate,HttpServletRequest request){
        Map<String, BigDecimal> result = new HashMap<>(2);
        try {
            // 清洗时间数据 2021-01-25 -> 2021-01
            LocalDate monthDate = LocalDate.parse(tradeDate, DateUtils.dateFormat);
            // 总天数，计算日均用
            int days = 0;
            if(DateUtils.checkIsCurrentMonth(tradeDate)){
                // 是当前月，天数为已经过去的天数
                days = monthDate.getDayOfMonth();
            }else{
                days = monthDate.lengthOfMonth();
            }
            // 得到上一个月的时间
            LocalDate lastMonth = monthDate.minusMonths(1);
            // 得到去年同期这个月的时间
            LocalDate lastYear = monthDate.minusYears(1);
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            BillOfDayEntity queryParam = new BillOfDayEntity();
            queryParam.setTradeDate(monthDate.format(DateUtils.dateFormatMonth));
            queryParam.setSource(userSession.getUser());
            // 本月的数据结果
            BillOfDayEntity _currentMonth = financialDeclareService.totalBalanceHard(queryParam);
            BigDecimal _currentMonthAccount = BigDecimal.ZERO;
            if (null != _currentMonth){
                // 本月总收入
                _currentMonthAccount = _currentMonth.getCurrencyNumber();
            }
            // 上月的数据结果
            queryParam.setTradeDate(lastMonth.format(DateUtils.dateFormatMonth));
            BillOfDayEntity _lastMonth = financialDeclareService.totalBalanceHard(queryParam);
            BigDecimal _lastMonthAccount = BigDecimal.ZERO;
            if (null != _lastMonth){
                // 上月的总收入
                _lastMonthAccount = _lastMonth.getCurrencyNumber();
            }
            // 去年同期这个月的数据结果
            queryParam.setTradeDate(lastYear.format(DateUtils.dateFormatMonth));
            BillOfDayEntity _lastYear = financialDeclareService.totalBalanceHard(queryParam);
            BigDecimal _lastYearAccount = BigDecimal.ZERO;
            if (null != _lastYear){
                // 去年同期这个月的总收入
                _lastYearAccount = _lastYear.getCurrencyNumber();
            }
            BigDecimal zero = BigDecimal.ZERO;
            // 计算日均
            BigDecimal avgAccount = _currentMonthAccount.divide(new BigDecimal(days),2, BigDecimal.ROUND_HALF_UP);
            // 计算环比 （本月的值-上月的值）÷上月的值(如果上月值为空，不计算)
            BigDecimal m2m = BigDecimal.ZERO;
            if(_lastMonthAccount != null && 0 != zero.compareTo(_lastMonthAccount)){
                m2m = (_currentMonthAccount.subtract(_lastMonthAccount)).divide(_lastMonthAccount,4, BigDecimal.ROUND_HALF_UP);
                m2m = m2m.multiply(BigDecimal.valueOf(100.0));
            }
            // 计算同比比 （本年的值-去年同期的值）÷去年同期的值(如果同期值为空，不计算)
            BigDecimal y2y = BigDecimal.ZERO;
            if(_lastYearAccount != null && 0 != zero.compareTo(_lastYearAccount)){
                y2y = (_currentMonthAccount.subtract(_lastYearAccount)).divide(_lastYearAccount,4, BigDecimal.ROUND_HALF_UP);
                y2y = y2y.multiply(BigDecimal.valueOf(100.0));
            }
            result.put("account",_currentMonthAccount);
            result.put("avg",avgAccount);
            result.put("m2m",m2m);
            result.put("y2y",y2y);
        } catch (Exception e) {
            result.put("account",new BigDecimal("0.0"));
            result.put("avg",new BigDecimal("0.0"));
            result.put("m2m",new BigDecimal("0.0"));
            result.put("y2y",new BigDecimal("0.0"));
            CurrentLineInfo.printCurrentLineInfo("统计收支增长率时发生异常", e, FinancialServiceImpl.class);
        }
        return ResultUtil.success(result);
    }

    /**
     * 收入比重
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return 收入率 总收支
     */
    @Override
    public Result<Object> incomePercentage(String tradeDate,HttpServletRequest request){
        Map<String, BigDecimal> result = new HashMap<>(2);
        try {
            // 清洗时间数据 2021-01-25 -> 2021-01
            LocalDate monthDate = LocalDate.parse(tradeDate, DateUtils.dateFormat);
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            BillOfDayEntity queryParam = new BillOfDayEntity();
            queryParam.setTradeDate(monthDate.format(DateUtils.dateFormatMonth));
            queryParam.setSource(userSession.getUser());
            BillOfDayEntity queryResult = financialDeclareService.totalBalanceHard(queryParam);
            if (null == queryResult){
                result.put("account",new BigDecimal("0.0"));
                result.put("percentage",new BigDecimal("0.0"));
            }else {
                result.put("account",queryResult.getCurrencyNumber());
                BigDecimal zero = BigDecimal.ZERO;
                BigDecimal percentage = BigDecimal.ZERO;
                if (0 != zero.compareTo(queryResult.getCurrencyNumber())){
                    percentage = (queryResult.getDeposited()).divide((queryResult.getCurrencyNumber()),4, BigDecimal.ROUND_HALF_UP);
                }
                result.put("percentage",percentage);
            }
        } catch (Exception e) {
            result.put("account",new BigDecimal("0.0"));
            result.put("percentage",new BigDecimal("0.0"));
            CurrentLineInfo.printCurrentLineInfo("统计收入比重时发生异常", e, FinancialServiceImpl.class);
        }
        return ResultUtil.success(result);
    }

    /**
     * 统计指定月份中各摘要的排名
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    @Override
    public Result<Object> orderByAmount(String tradeDate,HttpServletRequest request){
        try {
            // 清洗时间数据 2021-01-25 -> 2021-01
            LocalDate monthDate = LocalDate.parse(tradeDate, DateUtils.dateFormat);
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            List<BillOfAmountEntity> rankList = financialDeclareService.orderByAmount(monthDate.format(DateUtils.dateFormatMonth), userSession.getUser(), 0);
            return ResultUtil.success(rankList);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定月份中各摘要的排名时发生异常", e, FinancialServiceImpl.class);
            return ResultUtil.error(ResultEnum.ERROP);
        }
    }

    /**
     * 统计指定指定日期月份前6个月的账单
     * @param tradeDate 所在月份的日期
     * @param request 当前用户会话信息
     * @return
     */
    @Override
    public Result<Object> preSixMonthBill(String tradeDate,HttpServletRequest request){
        try {
            UserMemory userSession = (UserMemory) request.getSession().getAttribute("user");
            List<TransactionListEntity> billCollection = financialDeclareService.countPre6Financial(userSession.getUser(),tradeDate);
            return ResultUtil.success(billCollection);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计指定指定日期月份前6个月的账单时发生异常", e, FinancialServiceImpl.class);
            return ResultUtil.error(ResultEnum.ERROP);
        }
    }

}
