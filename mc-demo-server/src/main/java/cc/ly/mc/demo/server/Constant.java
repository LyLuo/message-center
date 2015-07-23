package cc.ly.mc.demo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by LuoHaowen on 2015/7/23.
 */
public class Constant {
    private static final Logger LOGGER = LoggerFactory.getLogger(Constant.class);

    public static String API_URL = "http://www.jieshuba.cn/api";

    private static final String CONFIG_FILE_PROPERTY_KEY = "configuration";

    private static final String DEFAULT_CONFIG_FILE_PATH = "config.properties";

    static {
        Properties properties = new Properties();
        String path = DEFAULT_CONFIG_FILE_PATH;
        try {
            if(System.getProperty(CONFIG_FILE_PROPERTY_KEY) != null){
                path = System.getProperty(CONFIG_FILE_PROPERTY_KEY);
            }
            properties.load(Constant.class.getClassLoader().getResourceAsStream(path));
            API_URL = properties.get("api.url").toString();
        } catch (Exception e) {
            LOGGER.error("can not found api properties, use default", e);
        }
    }
}
