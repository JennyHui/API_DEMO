package sql.po.user;

/**
 * Created by JennyHui on 2015/7/5
 * User表对应的实体类
 */
public class User {

    //实体类的属性和表的字段名称一一对应
    private String name;
    private String sex;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
