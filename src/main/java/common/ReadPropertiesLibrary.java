package common;

import java.util.Properties;

public class ReadPropertiesLibrary {

    public static Properties readFileProperties(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("config.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }

}
