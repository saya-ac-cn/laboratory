package ac.cn.saya.laboratory.persistent.business.dao;

import ac.cn.saya.laboratory.persistent.primary.dao.PrimaryJDBCConnection;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: BusinessBatchDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-09-22 22:51
 * @Description:
 */

@Repository("businessBatchDAO")
public class BusinessBatchDAO extends PrimaryJDBCConnection {

    /**
     * 调用存储过程查询近半年发表的动态
     * @param user 用户
     * @param endDate 终止日期
     * @return
     */
    public Map<String, Object> countPre6MonthNews(String user,String endDate) {
        Map<String, Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        try {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon.prepareCall("{Call countPre6News(?,?)}");
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

    /**
     * @描述 获取上一条和下一条动态或笔记的id
     * @参数 type 调用的存储过程类别 1 为动态 2 为笔记
     * @参数 id 传入的动态或笔记编号
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2019-03-03
     * @修改人和其它信息
     */
    public Map<String, String> getNewsNotesPreAndNext(Integer type, Integer Id) {
        Map<String, String> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        try {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = null;
            if (type == 1) {
                cs = sqlCon.prepareCall("{Call newsPreAndNext(?)}");
            } else {
                cs = sqlCon.prepareCall("{Call notesPreAndNext(?)}");
            }
            //设置参数
            cs.setInt(1, Id);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while (rs.next()) {
                String[] data = (rs.getString("id")).split(":");
                result.put(data[1], data[0]);
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
