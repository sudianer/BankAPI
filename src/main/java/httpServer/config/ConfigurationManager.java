package httpServer.config;

import util.JSONparser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
     private static ConfigurationManager myConfigurationManager;
     private static Configuration myCurrentConfiguration;

     private ConfigurationManager(){

     }

     public static ConfigurationManager getInstance(){
         if(myConfigurationManager == null){
             myConfigurationManager = new ConfigurationManager();
         }
         return myConfigurationManager;
     }

    /**
     * Загружает конфигурацию используя путь к файлу
     * @param filePath - путь к файлу конфигурации
     */
     public void loadConfigurationFile(String filePath) throws IOException {
         FileReader fileReader = null;
         try {
             fileReader = new FileReader(filePath);
         } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
         }
         StringBuilder stringBuilder = new StringBuilder();
         int i;
         while (true){
             try {
                 if (((i= fileReader.read()) == -1)) break;
             } catch (IOException e) {
                 throw new HttpConfigurationException(e);
             }
             stringBuilder.append((char)i);
         }
         JsonNode config = null;
         config = JSONparser.parse(stringBuilder.toString());
         myCurrentConfiguration = JSONparser.fromJson(config, Configuration.class);
     }

    /**
     * Возвращает текущую конфигурацию
     */
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new HttpConfigurationException("No configuration set");
        }
        return myCurrentConfiguration;
     }
}
