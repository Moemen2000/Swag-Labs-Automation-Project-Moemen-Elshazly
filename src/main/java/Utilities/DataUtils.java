package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class DataUtils {

    private static final String TEST_DATA_PATH ="src/test/resources/TestData/";


    //TODO: Read data from json file
    public static String get_Data_From_JsonFile(String jsonFileName , String field) {
        try
        {
            FileReader reader = new FileReader(TEST_DATA_PATH + jsonFileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(field).getAsString();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
        return "";
    }

    //TODO: Read data from properties file
    public static String get_Data_From_PropertiesFile(String propertiesFileName , String key) {
        try
        {
            Properties properties = new Properties();
            FileInputStream file_InputStream = new FileInputStream(TEST_DATA_PATH + propertiesFileName + ".properties");
            properties.load(file_InputStream);
            return properties.getProperty(key);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
        return "";
    }



}
