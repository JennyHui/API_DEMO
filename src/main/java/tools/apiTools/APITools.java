package tools.apiTools;

import tools.configuration.LoggerControler;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class APITools {
    static LoggerControler log = LoggerControler.getLogger(APITools.class);

    static HttpGet httpget;
    static HttpPost httpPost;

    /**
     * get 形式请求API
     *
     * @param uri url 和 对应参数
     * @return 返回相应信息
     * @throws URISyntaxException
     * @throws IOException
     */
    public static JSON getAPI(URI uri) throws URISyntaxException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            httpget = new HttpGet(uri);
            log.info("执行API请求" + httpget.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        log.error("请求错误，状态码为："+response.getStatusLine().getStatusCode());
                        throw new ClientProtocolException("意外的状态返回: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            JSONObject dataObject = JSONObject.fromObject(responseBody);
            return dataObject;
        } finally {
            httpclient.close();
        }
    }

    /**
     * Post 形式请求API
     *
     * @param uri url 和 对应参数
     * @return 返回相应信息
     * @throws URISyntaxException
     * @throws IOException
     */
    public static JSON postAPI(URI uri) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            httpPost = new HttpPost(uri);
            log.info("执行API接口请求" + httpPost.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("意外的状态返回: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            JSONObject dataObject = JSONObject.fromObject(responseBody);
            return dataObject;
        } finally {
            httpclient.close();
        }
    }
}
