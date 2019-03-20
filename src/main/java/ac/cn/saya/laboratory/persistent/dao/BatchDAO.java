package ac.cn.saya.laboratory.persistent.dao;

import ac.cn.saya.laboratory.entity.TransactionListEntity;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * @Title: BatchDAO
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-03 16:05
 * @Description:
 */

@Repository("batchDAO")
public class BatchDAO extends JDBCBaseConnection {

    /**
     * @描述 调用存储过程查询近半年发表的动态
     * @参数
     * @返回值  
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6MonthNews(String user){
        Map<String,Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call countPre6News(?)}");
            //设置参数
            cs.setString(1, user);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while(rs.next()){
                result.put(rs.getString("totalCount"),rs.getLong("count"));
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }


    /**
     * @描述 调用存储过程查询近半年活跃情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Logs(String user){
        Map<String,Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call countPre6Logs(?)}");
            //设置参数
            cs.setString(1, user);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while(rs.next()){
                result.put(rs.getString("totalCount"),rs.getLong("count"));
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }

    /**
     * @描述 调用存储过程查询近半年文件上传情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Files(String user){
        Map<String,Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call countPre6Files(?)}");
            //设置参数
            cs.setString(1, user);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while(rs.next()){
                result.put(rs.getString("totalCount"),rs.getLong("count"));
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }

    /**
     * @描述 调用存储过程查询近半年留言情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,Object> countPre6Board(){
        Map<String,Object> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call countPre6Board()}");
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while(rs.next()){
                result.put(rs.getString("totalCount"),rs.getLong("count"));
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }

    /**
     * @描述 调用存储过程查询近半年财政收支情况
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public List<TransactionListEntity> countPre6Financial(String user){
        List<TransactionListEntity> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call countPre6Financial(?)}");
            //设置参数
            cs.setString(1, user);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new ArrayList<>();
            while(rs.next()){
                result.add(new TransactionListEntity(rs.getString("totalCount"), rs.getDouble("deposited"), rs.getDouble("expenditure"), rs.getDouble("currencyNumber")));
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }

    /**
     * @描述 获取上一条动态和下一条动态的id
     * @参数
     * @返回值
     * @创建人  saya.ac.cn-刘能凯
     * @创建时间  2019-03-03
     * @修改人和其它信息
     */
    public Map<String,String> getNewsPreAndNext(Integer newsId){
        Map<String,String> result = null;
        SqlSession sqlSession = null;
        //连接对象
        Connection sqlCon = null;
        String flog = "";
        try
        {
            //获取sqlSession
            sqlSession = getSqlSession();
            //建立jdbc连接
            sqlCon =  sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
            CallableStatement cs = sqlCon .prepareCall("{Call newsPreAndNext(?)}");
            //设置参数
            cs.setInt(1, newsId);
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while(rs.next()){
                String[] data = (rs.getString("id")).split(":");
                result.put(data[1],data[0]);
            }
            cs.close();
            sqlCon.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            //及时关闭资源
            if(sqlSession != null)
            {
                sqlSession.close();
            }
            return result;
        }
    }

}