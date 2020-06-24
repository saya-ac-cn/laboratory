package ac.cn.saya.laboratory.persistent.financial.service;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import ac.cn.saya.laboratory.exception.MyException;
import ac.cn.saya.laboratory.persistent.financial.dao.FinancialBatchDAO;
import ac.cn.saya.laboratory.persistent.financial.dao.TransactionReadDAO;
import ac.cn.saya.laboratory.persistent.financial.dao.TransactionWriteDAO;
import ac.cn.saya.laboratory.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Title: FinancialDeclareService
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-22 21:47
 * @Description: 财政申报中间件
 */
@Service("financialDeclareService")
@Transactional(value = "financialTransactionManager",readOnly = true,propagation= Propagation.REQUIRED, isolation= Isolation.SERIALIZABLE, rollbackFor=MyException.class)
public class FinancialDeclareService {

    private static Logger logger = LoggerFactory.getLogger(FinancialDeclareService.class);

    @Resource
    @Qualifier("transactionWriteDAO")
    private TransactionWriteDAO transactionWriteDAO;

    @Resource
    @Qualifier("transactionReadDAO")
    private TransactionReadDAO transactionReadDAO;

    @Resource
    @Qualifier("financialBatchDAO")
    private FinancialBatchDAO batchDAO;

    /**
     * @描述 查询近半年财政收支情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public List<TransactionListEntity> countPre6Financial(String user) {
        try {
            return batchDAO.countPre6Financial(user);
        } catch (Exception e) {
            logger.error("查询近半年财政收支情况失败" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 获取所有交易类别数据
     *
     * @return
     */
    public List<TransactionTypeEntity> selectTransactionType() {
        try {
            List<TransactionTypeEntity> list = transactionReadDAO.selectTransactionType();
            if (list.size() <= 0) {
                return null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查询交易类别时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查看流水
     * 根据用户、类型、日期
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionPage(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionPage(entity);
        } catch (Exception e) {
            logger.error("查看流水时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查看流水总数
     * 根据用户、类型、日期
     *
     * @param entity
     * @return
     */
    public Long selectTransactionCount(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionCount(entity);
        } catch (Exception e) {
            logger.error("查看流水总数总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查看流水明细
     *
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionInfoPage(TransactionInfoEntity entity) {
        try {
            List<TransactionInfoEntity> list = transactionReadDAO.selectTransactionInfoPage(entity);
            if (list.size() <= 0) {
                return null;
            }
            return list;
        } catch (Exception e) {
            logger.error("查看流水明细发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查看流水明细总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionInfoCount(TransactionInfoEntity entity) {
        try {
            return transactionReadDAO.selectTransactionInfoCount(entity);
        } catch (Exception e) {
            logger.error("查看查看流水明细总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查询详细的流水明细
     *
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionFinalPage(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionFinalPage(entity);
        } catch (Exception e) {
            logger.error("查询详细的流水明细发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 查询详细的流水明细总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionFinalCount(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionFinalCount(entity);
        } catch (Exception e) {
            logger.error("查询详细的流水明细总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按天分页统计财务报表
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForDayPage(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForDayPage(entity);
        } catch (Exception e) {
            logger.error("按天分页统计财务报表异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按天统计财务报表流水总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForDayCount(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForDayCount(entity);
        } catch (Exception e) {
            logger.error("按天统计财务报表流水总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按月分页统计（只统计到上月的最后一天）
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForMonthPage(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForMonthPage(entity);
        } catch (Exception e) {
            logger.error("按月分页统计（只统计到上月的最后一天）异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按月统计（只统计到上月的最后一天）总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForMonthCount(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForMonthCount(entity);
        } catch (Exception e) {
            logger.error("按月统计（只统计到上月的最后一天）总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按年分页统计（只统计到上一年的最后一天）
     *
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionForYearPage(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForYearPage(entity);
        } catch (Exception e) {
            logger.error("按年分页统计（只统计到上一年的最后一天）异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 按年统计（只统计到上一年的最后一天）总数
     *
     * @param entity
     * @return
     */
    public Long selectTransactionForYearCount(TransactionListEntity entity) {
        try {
            return transactionReadDAO.selectTransactionForYearCount(entity);
        } catch (Exception e) {
            logger.error("按年统计（只统计到上一年的最后一天）总数时发生异常：" + Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 添加财政记录父+子
     *
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    public Result<Object> insertTransaction(TransactionListEntity entity) {
        try {
            //第一步遍历所有的财政字表，求得总和
            List<TransactionInfoEntity> requestInfoList = entity.getInfoList();
            // 存入总金额
            BigDecimal saveMoney = new BigDecimal("0.0");
            // 支取总金额
            BigDecimal takeMoney = new BigDecimal("0.0");
            for (TransactionInfoEntity item : requestInfoList) {
                if (item.getFlog() == 1) {
                    // 存入
                    saveMoney = saveMoney.add(item.getCurrencyNumber());
                    continue;
                }
                if (item.getFlog() == 2) {
                    // 支取
                    takeMoney = takeMoney.add(item.getCurrencyNumber());
                    continue;
                }
            }
            // 当日发生总金额（存入+支取）
            BigDecimal happenMoney = new BigDecimal(saveMoney.toString()).add(takeMoney);
            // 设置存入总金额
            entity.setDeposited(saveMoney);
            // 设置支取总金额
            entity.setExpenditure(takeMoney);
            // 设置交易总金额
            entity.setCurrencyNumber(happenMoney);
            // 开始写入父表的数据
            Integer tradeId = transactionWriteDAO.insertTransactionList(entity);
            if (tradeId > 0) {
                // 取出主键回填的值
                tradeId = entity.getTradeId();
                // 父表操作成功
                // 开始写入子表的数据
                for (TransactionInfoEntity item : requestInfoList) {
                    // 设置流水号，也就是在父表写入成功后，主键回填的值
                    item.setTradeId(tradeId);
                    transactionWriteDAO.insertTransactionInfo(item);
                }
                return ResultUtil.success();
            } else {
                logger.warn("添加财政记录父+子异常");
                throw new MyException(ResultEnum.ERROP);
            }
        } catch (MyException e) {
            e.printStackTrace();
            logger.warn("添加财政记录父+子异常");
            throw new MyException(ResultEnum.DB_ERROR);
        }
    }

    /**
     * 修改财政记录父
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result<Object> updateTransaction(TransactionListEntity entity) {
        // 只允许修改交易类别以及交易摘要
        if (entity == null || entity.getTradeId() == null || entity.getTradeType() == null || StringUtils.isEmpty(entity.getTransactionAmount())) {
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            try {
                // 读取交易总览
                TransactionListEntity preview = transactionReadDAO.selectTransactionList(entity);
                if (null == preview || !DateUtils.checkIsCurrentMonth(preview.getTradeDate())){
                    return ResultUtil.error(ResultEnum.FORBID_POWER);
                }
                if (transactionWriteDAO.updateTransactionList(entity) > 0) {
                    return ResultUtil.success();
                } else {
                    logger.warn("修改财政记录异常");
                    throw new MyException(ResultEnum.DB_ERROR);
                }
            } catch (MyException e) {
                e.printStackTrace();
                logger.warn("修改财政记录父异常");
                throw new MyException(ResultEnum.DB_ERROR);
            }
        }
    }

    /**
     * 这里是级联删除
     * 删除财政记录父+子
     *
     * @param entity
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result<Object> deleteTransaction(TransactionListEntity entity, String user) {
        if (entity == null || entity.getTradeId() == null) {
            throw new MyException(ResultEnum.NOT_PARAMETER);
        } else {
            try {
                TransactionListEntity preview = transactionReadDAO.selectTransactionList(entity);
                if (null == preview || !DateUtils.checkIsCurrentMonth(preview.getTradeDate())){
                    return ResultUtil.error(ResultEnum.FORBID_POWER);
                }
                if (transactionWriteDAO.deleteTransactionList(entity.getTradeId(), user) > 0) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error(-1, "删除失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("删除财政记录父+子异常");
                throw new MyException(ResultEnum.DB_ERROR);
            }
        }
    }

    /**
     * 单行
     * 添加财政子记录
     *
     * @param entity
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result<Object> insertTransactioninfo(TransactionInfoEntity entity, String user) {
        TransactionListEntity preview = transactionReadDAO.selectTransactionList(new TransactionListEntity(entity.getTradeId(),user));
        if (null == preview || !DateUtils.checkIsCurrentMonth(preview.getTradeDate())){
            return ResultUtil.error(ResultEnum.FORBID_POWER);
        }
        // 先把子记录进行写入
        if (transactionWriteDAO.insertTransactionInfo(entity) > 0) {
            // 查询本流水号下的所有子记录
            TransactionInfoEntity queryEntity = new TransactionInfoEntity();
            queryEntity.setTradeId(entity.getTradeId());
            // 财政子表记录总行数
            Long itemConut = transactionReadDAO.selectTransactionInfoCount(queryEntity);
            if (itemConut > 0) {
                queryEntity.setStartLine(0);
                queryEntity.setEndLine(itemConut.intValue());
                List<TransactionInfoEntity> itemList = transactionReadDAO.selectTransactionInfoPage(queryEntity);
                // 存入总金额
                BigDecimal saveMoney = new BigDecimal("0.0");
                // 支取总金额
                BigDecimal takeMoney = new BigDecimal("0.0");
                TransactionListEntity queryParentEntity = new TransactionListEntity();
                // 根据主键进行查询，最多只能有一条
                queryParentEntity.setTradeId(entity.getTradeId());
                queryParentEntity.setStartLine(0);
                queryParentEntity.setEndLine(1);
                List<TransactionListEntity> transactionPage = transactionReadDAO.selectTransactionPage(queryParentEntity);
                if (transactionPage.size() > 0) {
                    TransactionListEntity writeEntity = transactionPage.get(0);
                    for (TransactionInfoEntity item : itemList) {
                        if (item.getFlog() == 1) {
                            // 存入
                            saveMoney = saveMoney.add(item.getCurrencyNumber());
                            continue;
                        }
                        if (item.getFlog() == 2) {
                            // 支取
                            takeMoney = takeMoney.add(item.getCurrencyNumber());
                            continue;
                        }
                    }
                    // 当日发生总金额（存入+支取）
                    BigDecimal happenMoney = new BigDecimal(saveMoney.toString()).add(takeMoney);
                    // 设置存入总金额
                    writeEntity.setDeposited(saveMoney);
                    // 设置支取总金额
                    writeEntity.setExpenditure(takeMoney);
                    // 设置交易总金额
                    writeEntity.setCurrencyNumber(happenMoney);
                    writeEntity.setSource(user);
                    // 开始写入父表的数据
                    if (transactionWriteDAO.updateTransactionList(writeEntity) > 0) {
                        return ResultUtil.success();
                    } else {
                        throw new MyException(ResultEnum.RollBACK);
                    }
                } else {
                    return ResultUtil.error(-1, "财政父表数据丢失");
                }
            } else {
                logger.warn("写入财政字表失败");
                throw new MyException(ResultEnum.ERROP);
            }
        } else {
            logger.warn("写入财政子表失败");
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 单行
     * 修改财政子记录
     *
     * @param entity
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result<Object> updateTransactioninfo(TransactionInfoEntity entity, String user) {
        TransactionListEntity preview = transactionReadDAO.selectTransactionList(new TransactionListEntity(entity.getTradeId(),user));
        if (null == preview || !DateUtils.checkIsCurrentMonth(preview.getTradeDate())){
            return ResultUtil.error(ResultEnum.FORBID_POWER);
        }
        // 先把子记录进行写入
        if (transactionWriteDAO.updateTransactionInfo(entity) > 0) {
            // 查询本流水号下的所有子记录
            TransactionInfoEntity queryEntity = new TransactionInfoEntity();
            queryEntity.setTradeId(entity.getTradeId());
            // 财政子表记录总行数
            Long itemConut = transactionReadDAO.selectTransactionInfoCount(queryEntity);
            if (itemConut > 0) {
                queryEntity.setStartLine(0);
                queryEntity.setEndLine(itemConut.intValue());
                List<TransactionInfoEntity> itemList = transactionReadDAO.selectTransactionInfoPage(queryEntity);
                // 存入总金额
                BigDecimal saveMoney = new BigDecimal("0.0");
                // 支取总金额
                BigDecimal takeMoney = new BigDecimal("0.0");
                TransactionListEntity queryParentEntity = new TransactionListEntity();
                // 根据主键进行查询，最多只能有一条
                queryParentEntity.setTradeId(entity.getTradeId());
                queryParentEntity.setStartLine(0);
                queryParentEntity.setEndLine(1);
                List<TransactionListEntity> transactionPage = transactionReadDAO.selectTransactionPage(queryParentEntity);
                if (transactionPage.size() > 0) {
                    TransactionListEntity writeEntity = transactionPage.get(0);
                    for (TransactionInfoEntity item : itemList) {
                        if (item.getFlog() == 1) {
                            // 存入
                            saveMoney = saveMoney.add(item.getCurrencyNumber());
                            continue;
                        }
                        if (item.getFlog() == 2) {
                            // 支取
                            takeMoney = takeMoney.add(item.getCurrencyNumber());
                            continue;
                        }
                    }
                    // 当日发生总金额（存入+支取）
                    BigDecimal happenMoney = new BigDecimal(saveMoney.toString()).add(takeMoney);
                    // 设置存入总金额
                    writeEntity.setDeposited(saveMoney);
                    // 设置支取总金额
                    writeEntity.setExpenditure(takeMoney);
                    // 设置交易总金额
                    writeEntity.setCurrencyNumber(happenMoney);
                    writeEntity.setSource(user);
                    // 开始写入父表的数据
                    if (transactionWriteDAO.updateTransactionList(writeEntity) > 0) {
                        return ResultUtil.success();
                    } else {
                        throw new MyException(ResultEnum.RollBACK);
                    }
                } else {
                    return ResultUtil.error(-1, "财政父表数据丢失");
                }
            } else {
                logger.warn("修改财政子表失败");
                throw new MyException(ResultEnum.ERROP);
            }
        } else {
            logger.warn("修改财政子表失败");
            throw new MyException(ResultEnum.ERROP);
        }
    }

    /**
     * 删除财政子记录
     *
     * @param entity
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public Result<Object> deleteTransactioninfo(TransactionInfoEntity entity, String user) {
        TransactionListEntity preview = transactionReadDAO.selectTransactionList(new TransactionListEntity(entity.getTradeId(),user));
        if (null == preview || !DateUtils.checkIsCurrentMonth(preview.getTradeDate())){
            return ResultUtil.error(ResultEnum.FORBID_POWER);
        }
        // 查询本流水号下的所有子记录
        TransactionInfoEntity queryEntity = new TransactionInfoEntity();
        queryEntity.setTradeId(entity.getTradeId());
        // 财政子表记录总行数
        Long itemConut = transactionReadDAO.selectTransactionInfoCount(queryEntity);
        if (itemConut > 0) {
            if (itemConut == 1 || itemConut.equals(1)) {
                // 只有一条记录时，直接调用父表的方法级联删除即可
                if (transactionWriteDAO.deleteTransactionList(entity.getTradeId(), user) > 0) {
                    return ResultUtil.success("End");
                } else {
                    return ResultUtil.error(-1, "删除失败");
                }
            } else {
                // 先删除子表，然后修改父表的值
                if (transactionWriteDAO.deleteTransactionInfo(entity.getId()) > 0) {
                    // 查询本流水号下的所有子记录
                    // 财政子表记录总行数
                    itemConut = transactionReadDAO.selectTransactionInfoCount(queryEntity);
                    if (itemConut > 0) {
                        queryEntity.setStartLine(0);
                        queryEntity.setEndLine(itemConut.intValue());
                        List<TransactionInfoEntity> itemList = transactionReadDAO.selectTransactionInfoPage(queryEntity);
                        // 存入总金额
                        BigDecimal saveMoney = new BigDecimal("0.0");
                        // 支取总金额
                        BigDecimal takeMoney = new BigDecimal("0.0");
                        TransactionListEntity queryParentEntity = new TransactionListEntity();
                        // 根据主键进行查询，最多只能有一条
                        queryParentEntity.setTradeId(entity.getTradeId());
                        queryParentEntity.setStartLine(0);
                        queryParentEntity.setEndLine(1);
                        List<TransactionListEntity> transactionPage = transactionReadDAO.selectTransactionPage(queryParentEntity);
                        if (transactionPage.size() > 0) {
                            TransactionListEntity writeEntity = transactionPage.get(0);
                            for (TransactionInfoEntity item : itemList) {
                                if (item.getFlog() == 1) {
                                    // 存入
                                    saveMoney = saveMoney.add(item.getCurrencyNumber());
                                    continue;
                                }
                                if (item.getFlog() == 2) {
                                    // 支取
                                    takeMoney = takeMoney.add(item.getCurrencyNumber());
                                    continue;
                                }
                            }
                            // 当日发生总金额（存入+支取）
                            BigDecimal happenMoney = new BigDecimal(saveMoney.toString()).add(takeMoney);
                            // 设置存入总金额
                            writeEntity.setDeposited(saveMoney);
                            // 设置支取总金额
                            writeEntity.setExpenditure(takeMoney);
                            // 设置交易总金额
                            writeEntity.setCurrencyNumber(happenMoney);
                            //在session中取出管理员的信息   最后放入的都是 用户名 不是邮箱
                            writeEntity.setSource(user);
                            // 开始写入父表的数据
                            if (transactionWriteDAO.updateTransactionList(writeEntity) > 0) {
                                // 删除后父表及子表还有相关数据，返回前端Exist，不关闭弹框
                                return ResultUtil.success("Exist");
                            } else {
                                throw new MyException(ResultEnum.RollBACK);
                            }
                        } else {
                            return ResultUtil.error(-1, "财政父表数据丢失");
                        }
                    } else {
                        logger.warn("修改财政子表失败");
                        throw new MyException(ResultEnum.ERROP);
                    }
                } else {
                    return ResultUtil.error(-1, "删除失败");
                }
            }
        } else {
            return ResultUtil.error(-1, "记录已经不存在");
        }
    }

}
