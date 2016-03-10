import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesReader {

    private static int port = 8080;
    private static String log4j_configFile = "";

    public static void readProperties() throws Exception{
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(new FileInputStream("server.properties"),"UTF-8"));

            port = Integer.valueOf(prop.getProperty("port"));
            log4j_configFile = prop.getProperty("log4j_configFile");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static int getPort(){return port;}
    public static String getLog4jConfigFile() {return log4j_configFile;}
}
