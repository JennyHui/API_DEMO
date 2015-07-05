package tools.configuration;

import tools.apiTools.JsonFormatTool;
import net.sf.json.JSON;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public final class LoggerControler {
    private static String path = System.getProperty("user.dir") + File.separator+"src"+ File.separator+"main"+ File.separator+"resources"+ File.separator;
    private static Logger logger = null;
    private static LoggerControler logg = null;

    public static LoggerControler getLogger(Class<?> T) {
        if (logger == null) {
            Properties props = new Properties();

            try {
                String envPaht = path + "log4j.properties";
                InputStream is = new FileInputStream(envPaht);
                props.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }

            PropertyConfigurator.configure(props);
            logger = Logger.getLogger(T);
            logg = new LoggerControler();
        }
        return logg;
    }

    /**
     * 重写logger方法
     */
    public void info(String msg) {
        logger.info(msg);
//        Reporter.log("Reporter:" + getTime() + "===>" + msg);
    }
    public void info(JSON msg) {
        logger.info(JsonFormatTool.formatJson(String.valueOf(msg)));
//        Reporter.log("Reporter:" + getTime() + "===>" + msg);
    }


    public void debug(String msg) {
        logger.debug(msg);
//        Reporter.log("Reporter:" + getTime() + "===>" + msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
//        Reporter.log("Reporter:" + getTime() + "===>" + msg);
    }

    public void error(String msg) {
        logger.error(msg);
//        Reporter.log("Reporter:" + getTime() + "===>" + msg);
    }

    public void error(StringBuilder message) {
        logger.error(message.toString());
    }

    public void info(StringBuilder message) {
        logger.info(message.toString());
    }

    public void warn(StringBuilder message) {
        logger.warn(message.toString());
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        String str = sdf.format(ca.getTime());
        return str;
    }


}
