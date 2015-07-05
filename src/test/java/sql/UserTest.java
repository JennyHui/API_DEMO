package sql;

import org.apache.ibatis.session.SqlSession;
import sql.po.user.User;
import sql.po.user.UserDao;
import sql.po.user.UserImpl;
import sql.po.address.AddressMapperDao;
import tools.sqltools.MyBatisUtil;

/**
 * Created by JennyHui on 2015/7/5
 */
public class UserTest {

    static UserDao userDao = new UserImpl();
    public static User name(String name) {
        User a = userDao.getUserByName(name);
        return a;
    }
}
