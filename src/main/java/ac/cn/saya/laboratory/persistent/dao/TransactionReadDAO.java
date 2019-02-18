package ac.cn.saya.laboratory.persistent.dao;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.entity.TransactionTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: TransactionReadDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/11/1 22:06
 * @Description:
 * 财政数据库只读DAO
 */

@Mapper
@Repository("transactionReadDAO")
public interface TransactionReadDAO {

    /**
     * 获取交易类别表
     * @return
     */
    public List<TransactionTypeEntity> selectTransactionType();

    /**
     * 查看流水
     * @param entity
     * @return
     */
    public List<TransactionListEntity> selectTransactionPage(TransactionListEntity entity);

    /**
     * 查看流水总数
     * @param entity
     * @return
     */
    public Long selectTransactionCount(TransactionListEntity entity);

    /**
     * 查看流水明细
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionInfoPage(TransactionInfoEntity entity);

    /**
     * 查看流水明细总数
     * @param entity
     * @return
     */
    public Long selectTransactionInfoCount(TransactionInfoEntity entity);

    /**
     * 查询详细的流水明细
     * @param entity
     * @return
     */
    public List<TransactionInfoEntity> selectTransactionFinalPage(TransactionListEntity entity);

    /**
     * 查询详细的流水明细总数
     * @param entity
     * @return
     */
    public Long selectTransactionFinalCount(TransactionListEntity entity);

}
