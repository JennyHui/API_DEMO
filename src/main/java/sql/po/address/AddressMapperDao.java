package sql.po.address;

import org.apache.ibatis.annotations.Select;

/**
 * Created by JennyHui on 2015/7/5
 * 直接通过映射接口
 * 方法二 不用写实现类 直接调用接口就可以
 */
public interface AddressMapperDao {

    //使用@Select注解指明getAddressByCity方法要执行的SQL
    @Select("select * from address where city=#{city}")
    public Address getAddressByCity(String city);
}
