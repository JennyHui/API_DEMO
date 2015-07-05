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
        //�õ�AddressMapperDao�ӿڵ�ʵ�������AddressMapperDao�ӿڵ�ʵ���������sqlSession.getMapper(AddressMapperDao.class)��̬��������
        AddressMapperDao mapper = sqlSession.getMapper(AddressMapperDao.class);
        Address address = mapper.getAddressByCity(city);
        sqlSession.close();
        return address;
    }
}
