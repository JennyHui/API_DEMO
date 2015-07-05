package sql.po.address;

import org.apache.ibatis.annotations.Select;

/**
 * Created by JennyHui on 2015/7/5
 * ֱ��ͨ��ӳ��ӿ�
 * ������ ����дʵ���� ֱ�ӵ��ýӿھͿ���
 */
public interface AddressMapperDao {

    //ʹ��@Selectע��ָ��getAddressByCity����Ҫִ�е�SQL
    @Select("select * from address where city=#{city}")
    public Address getAddressByCity(String city);
}
