package ac.cn.saya.laboratory.persistent.dao;

import ac.cn.saya.laboratory.entity.TransactionInfoEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title: TransactionWriteDAO
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/12/27 22:29
 * @Description:
 * 财政数据库修改DAO
 */

@Mapper
@Repository("transactionWriteDAO")
public interface TransactionWriteDAO {

    /**
     * @描述 写入到财政明细表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer insertTransactionInfo(TransactionInfoEntity entity);

    /**
     * @描述 写入到财政父表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer insertTransactionList(TransactionListEntity entity);

    /**
     * @描述 修改财政明细表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer updateTransactionInfo(TransactionInfoEntity entity);

    /**
     * @描述 修改财政父表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer updateTransactionList(TransactionListEntity entity);

    /**
     * @描述 删除财政明细表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer deleteTransactionInfo(@Param(value = "id") Integer id);

    /**
     * @描述 删除财政父表
     * @参数  [entity]
     * @返回值  java.lang.Integer
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2018/12/27
     * @修改人和其它信息
     */
    public Integer deleteTransactionList(@Param(value = "tradeId") Integer tradeId, @Param(value = "source") String source);

}
