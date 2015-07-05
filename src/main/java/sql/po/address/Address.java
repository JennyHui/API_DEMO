package sql.po.address;

/**
 * Created by JennyHui on 2015/7/5
 * Address表对应的实体类
 */
public class Address {
    private int ZIP_CODE;
    private String City;

    public int getZIP_CODE() {
        return ZIP_CODE;
    }

    public void setZIP_CODE(int ZIP_CODE) {
        this.ZIP_CODE = ZIP_CODE;
    }
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
