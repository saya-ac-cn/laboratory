package ac.cn.saya.laboratory.persistent.financial.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

/**
 * @Title: FinancialJDBCConnection
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-03 16:03
 * @Description: 基类Dao
 * 自动实现相关的Mybatis变量
 * 把配置文件中的been注入到此
 */

public class FinancialJDBCConnection {

    @Resource
    @Qualifier("financialSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSession getSqlSession() {
        //打开SqlSession会话
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

}
