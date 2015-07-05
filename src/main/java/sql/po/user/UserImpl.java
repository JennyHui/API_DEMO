package sql.po.user;

import org.apache.ibatis.session.SqlSession;
import tools.sqltools.MyBatisUtil;

/**
 * Created by JennyHui on 2015/7/5
 */
public class UserImpl implements UserDao{

/*方法一*/
    /**
     * 映射sql的标识字符串，
     * User是userMapper.xml文件中mapper标签的namespace属性的值，
     * user_name是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
     */
    private final String  getUserByNameSQL = "User.user_name"; //映射sql的标识字符串

    @Override
    public User getUserByName(String name) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession("apiDB", true);

        //执行查询返回一个唯一user对象的sql
        User user = sqlSession.selectOne(getUserByNameSQL, name);
        sqlSession.close();
        return user;
    }





}
