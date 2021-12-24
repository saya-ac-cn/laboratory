package ac.cn.saya.laboratory;

import ac.cn.saya.laboratory.entity.BillOfDayEntity;
import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.persistent.financial.dao.BillDAO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: DAOTest
 * @ProjectName laboratory
 * @Description: TODO
 * @Author saya
 * @Date: 2020/10/18 23:23
 * @Description:
 */

@SpringBootTest(classes = LaboratoryApplication.class)
public class DAOTest {

    @Resource
    private BillDAO billDAO;

    @Test
    public void queryBillByDayTest(){
        TransactionListEntity query = new TransactionListEntity();
        query.setTradeDate("2018-06");
        List<BillOfDayEntity> list = billDAO.queryBillByDay(query);
        return;
    }

    @Test
    public void queryBillDetail(){
        TransactionListEntity query = new TransactionListEntity();
        query.setTradeId(703);
        query.setSource("Pandora");
        TransactionListEntity entity = billDAO.queryBillDetail(query);
        return;
    }

}
