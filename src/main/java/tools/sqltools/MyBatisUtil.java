package tools.sqltools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {

	/**
	 * 获取SqlSessionFactory
	 * @return SqlSessionFactory
	 */
	public static SqlSessionFactory getSqlSessionFactory(String environmentId) {
		InputStream is = null;
		try {
			//mybatis的配置文件
			is = Resources.getResourceAsStream("config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
		//构建sqlSession的工厂
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is,environmentId);
		return factory;
	}


	/**
	 * 获取SqlSession
	 * @param isAutoCommit
	 *         true 表示创建的SqlSession对象在执行完SQL之后会自动提交事务
	 *         false 表示创建的SqlSession对象在执行完SQL之后不会自动提交事务，这时就需要我们手动调用sqlSession.commit()提交事务
	 * @return SqlSession
	 */
	public static SqlSession getSqlSession(String environmentId,boolean isAutoCommit) {
		return getSqlSessionFactory(environmentId).openSession(isAutoCommit);
	}
}