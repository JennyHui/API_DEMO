package sql;

import org.apache.ibatis.session.SqlSession;
import sql.po.address.Address;
import sql.po.address.AddressMapperDao;
import sql.po.user.User;
import tools.sqltools.MyBatisUtil;

/**
 * Created by JennyHui on 2015/7/5
 */
public class AddressTest {

    public static Address city(String city) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession("apiDB", true);
        //得到AddressMapperDao接口的实现类对象，AddressMapperDao接口的实现类对象由sqlSession.getMapper(AddressMapperDao.class)动态构建出来
        AddressMapperDao mapper = sqlSession.getMapper(AddressMapperDao.class);
        Address address = mapper.getAddressByCity(city);
        sqlSession.close();
        return address;
    }
}
