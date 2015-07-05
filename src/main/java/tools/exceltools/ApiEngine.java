package tools.exceltools;

import tools.apiTools.APITools;
import tools.configuration.LoggerControler;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;

public class ApiEngine {

    public static LoggerControler log = LoggerControler.getLogger(ApiEngine.class);

    /**
     * 根据传入的TID匹配找出需要的api所在行
     *
     * @param tid
     * @return
     */
    public static List getApiData(String tid) {

        int DataRow = 1;
        List apiData = null;
        try {
            List tidList;
            tidList = ExcelEngine.getColData(0);
            for (int i = 1; i < tidList.size(); i++) {
                String excelTid = (String) tidList.get(i);
                //System.out.println(excelTid);
                if (excelTid.equals(tid)) {
                    DataRow = i;
                    break;
                    //System.out.println("行数："+i);
                }
            }
            //找出api的行数据
            apiData = ExcelEngine.getRowData(DataRow);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiData;
    }

    /**
     * 请求链接拼接
     *
     * @param tid        哪一行的excel数据
     * @param parameters 参数
     * @return uri
     */
    public static URI returnURI(String tid, String... parameters) {
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "excel" + File.separator + "api.xls";
        ExcelEngine.filepath = path;
        ExcelEngine.sheetname = "api";
        // 从excel 拿数据
        List apiData = getApiData(tid);
        String getorpost = ((String) apiData.get(2)).toLowerCase();
//        System.out.println(getorpost);
        String scheme = (String) apiData.get(3);
        String apiHost = (String) apiData.get(4);
        String apiPath = (String) apiData.get(5);
        // URI 拼接
        StringBuffer stringBuffer = new StringBuffer(scheme + "://" + apiHost + apiPath + "?");
        HashMap<String, String> hashmap = new HashMap();
        URI uri;

        int p = 0;
        while (p < parameters.length) {
            //HashMap<key,value>
            hashmap.put((String) apiData.get(p + 6), parameters[p]);
            p = p + 1;
        }

        if(p==0){
            StringBuffer uriWOParameters= new StringBuffer(scheme + "://" + apiHost + apiPath);
            String url = uriWOParameters.toString();
            uri = URI.create(url);
        }

        else{
            int num = 0;
            for (Entry<String, String> entry : hashmap.entrySet()) {
                num++;
                if (num == hashmap.size()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue());
                } else {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
            }
            String url = stringBuffer.toString();
            uri = URI.create(url);
        }

        return uri;
    }


    /**
     * 登录api
     *
     * @return ticket_id
     */
    public static JSONObject taquAPI(String tid, String... Parameters) {

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "excel" + File.separator + "api.xls";
        ExcelEngine.filepath = path;
        ExcelEngine.sheetname = "api";
        // 从excel 拿数据
        List apiData = getApiData(tid);
        String getorpost = ((String) apiData.get(2)).toLowerCase();

        URI uri = ApiEngine.returnURI(tid, Parameters);

        JSON json = null;
        try {
            if (getorpost.equals("get")) {
                json = APITools.getAPI(uri);
            } else {
                json = APITools.postAPI(uri);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject loginJson = JSONObject.fromObject(json);
        return loginJson;
    }

}
