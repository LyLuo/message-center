package cc.ly.mc.demo.server.cc.ly.mc.demo.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2015/7/6.
 */
public class ApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiServer.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String CODE = "code";

    private static final String DATA = "data";

    public static final int SUCCESS = 0;

    public static final int SYSTEM_ERROR = 1000;

    private static String API_URL = "http://www.jieshuba.cn/api";

    static {
        Properties properties = new Properties();
        try {
            properties.load(ApiServer.class.getClassLoader().getResourceAsStream("api.properties"));
            API_URL = properties.get("api.url").toString();
        } catch (IOException e) {
            LOGGER.error("can not found api properties, use default", e);
        }
    }

    public Integer validateToken(Integer id, String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.prepareGet(API_URL + "/users/current");
        builder.addHeader(AUTHORIZATION_HEADER, token);
        Future<Response> f = builder.execute();
        Response r;
        try {
            r = f.get();
            JsonNode json = objectMapper.readTree(r.getResponseBody());
            if (json.has(CODE) && json.get(CODE).intValue() == 0) {
                if (json.get(DATA).get("id").intValue() == id.intValue()) {
                    return SUCCESS;
                }
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.error("failed to validate token", e);
        } finally {
            asyncHttpClient.closeAsynchronously();
        }
        return SYSTEM_ERROR;
    }
}
