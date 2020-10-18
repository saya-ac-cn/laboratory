package ac.cn.saya.laboratory.persistent.financial.dao;

import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 账单DAO（主要由小程序发起）
 * @Title: BillDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/18 22:45
 * @Description: 本类下只提供查询业务
 */

@Mapper
public interface BillDAO {

    /**
     * 按天分页查询账单
     * @param param
     * @return
     */
    public List<BillOfDayEntity> queryBillByDay(TransactionListEntity param);

}
