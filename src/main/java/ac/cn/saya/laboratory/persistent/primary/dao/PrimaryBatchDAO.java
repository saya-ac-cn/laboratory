package ac.cn.saya.laboratory.persistent.primary.dao;

import ac.cn.saya.laboratory.entity.TransactionListEntity;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * @Title: PrimaryBatchDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-03 16:05
 * @Description:
 */

@Repository("primaryBatchDAO")
public class PrimaryBatchDAO extends PrimaryJDBCConnection {

    /**
     * 调用存储过程查询近半年活跃情况
     * @param user 用户
     * @param endDate 终止日期
     * @return
     */
    public Map<String, Object> countPre6Logs(String user,String endDate) {
        Map<String, Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        try {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon.prepareCall("{Call countPre6Logs(?,?)}");
            //设置参数
            cs.setString(1, user);
            cs.setString(2, endDate);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while (rs.next()) {
                result.put(rs.getString("totalCount"), rs.getLong("count"));
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
