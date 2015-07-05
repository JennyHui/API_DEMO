package api;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import sql.AddressTest;
import sql.po.address.Address;
import tools.asserttool.TaquAssert;
import tools.configuration.LoggerControler;
import tools.exceltools.ApiEngine;

/**
 * Created by JennyHui on 2015/7/5
 */
public class GetAddressByCityTest {
    public static LoggerControler log = LoggerControler.getLogger(GetAddressByCityTest.class);

    final String TID = "GetAddressByCityTest";
    final String CITY = "Amoy";
    String ZIP_CODE;

    @Test
    public void getUserByNameTest() {
        JSONObject json = ApiEngine.taquAPI(TID);
        log.info(json);

        Address address = AddressTest.city(CITY);
        ZIP_CODE = String.valueOf(address.getZIP_CODE());

        String api_ZipCode = json.getString("ZIP Code");
        TaquAssert.assertEquals("” ±‡–£—È", ZIP_CODE, api_ZipCode);

    }
}
