package api;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import sql.po.user.User;
import sql.UserTest;
import tools.asserttool.TaquAssert;
import tools.configuration.LoggerControler;
import tools.exceltools.ApiEngine;

/**
 * Created by JennyHui on 2015/7/4
 */
public class GetUserByNameTest {
    public static LoggerControler log = LoggerControler.getLogger(GetUserByNameTest.class);

    final String TID = "GetUserByNameTest";
    final String NAME = "JennyHui";
    String age;
    String sex;

    @Test
    public void getUserByNameTest() {
        JSONObject json = ApiEngine.taquAPI(TID);
        log.info(json);

        User user = UserTest.name(NAME);
        age = String.valueOf(user.getAge());
        sex = user.getSex();

        String api_name = json.getString("name");
        TaquAssert.assertEquals("����У��",NAME, api_name);
        String api_sex = json.getString("sex");
        TaquAssert.assertEquals("�Ա�У��",sex, api_sex);
        String api_age = json.getString("age");
        TaquAssert.assertEquals("����У��",age, api_age);

    }
}
