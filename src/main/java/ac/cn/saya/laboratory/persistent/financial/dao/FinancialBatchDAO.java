package ac.cn.saya.laboratory.persistent.financial.dao;

import ac.cn.saya.laboratory.entity.TransactionListEntity;
import ac.cn.saya.laboratory.persistent.primary.dao.PrimaryJDBCConnection;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FinancialBatchDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-03 16:05
 * @Description:
 */

@Repository("financialBatchDAO")
public class FinancialBatchDAO extends PrimaryJDBCConnection {

    /**
     * @描述 调用存储过程查询近半年财政收支情况
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public List<TransactionListEntity> countPre6Financial(String user) {
        List<TransactionListEntity> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        try {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon.prepareCall("{Call countPre6Financial(?)}");
            //设置参数
            cs.setString(1, user);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(new TransactionListEntity(rs.getString("totalCount"), new BigDecimal(rs.getString("deposited")), new BigDecimal(rs.getString("expenditure")), new BigDecimal(rs.getString("currencyNumber"))));
            }
            cs.close();
            sqlCon.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //及时关闭资源
            if (sqlSession != null) {
                sqlSession.close();
            }
            return result;
        }
    }


}
