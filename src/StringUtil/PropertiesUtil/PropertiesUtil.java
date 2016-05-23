package StringUtil.PropertiesUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import DatebaseUtil.Util.DatebaseDTO;
import LoggerUtil.Logger;

/**
 * @author Administrator
 *
 */
public class PropertiesUtil {
	private static final String DIR = System.getProperty("user.dir");
	private static final String DBCONFIG = "dbConfig.properties";
	private static DatebaseDTO dbConfigDTO = new DatebaseDTO();
	private static final String CONFIG="config.properties";

	/**
	 * 取数据库配置
	 * @return
	 */
	public static DatebaseDTO readPorperties() {
		String path = DIR + "\\" + DBCONFIG;
		File file = new File(path);
		if (!file.isFile() && !file.exists()) {
			new Logger().Logger("文件错误或者不存在！");
		}
		return readPropertiesFile(path);

	}
	public static String getConfigPath(){
		return DIR+"\\"+CONFIG;
	}

	/**
	 * 读数据库存配置文件
	 * @param filename
	 * @return
	 */
	public static DatebaseDTO readPropertiesFile(String filename) {
		Properties prop = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filename);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			prop.load(bf);
			dbConfigDTO.setDriver(prop.getProperty("Driver").trim());
			dbConfigDTO.setUrl(prop.getProperty("Url").trim());
			dbConfigDTO.setUser(prop.getProperty("User").trim());
			dbConfigDTO.setPassword(prop.getProperty("Password").trim());
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dbConfigDTO;
	}
    /**
     * 读取资源文件,并处理中文乱码
     * @param filename
     * @return
     */
    public static Properties readPropertiesFileObj(String filename) {
        Properties properties = new OrderedProperties();
        try {
            InputStream inputStream = new FileInputStream(filename);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            properties.load(bf);
            inputStream.close(); // 关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    
    /**
     * 写资源文件，含中文
     * @param filename
     * @param properties
     */
    public static void writePropertiesFileObj(String filename, Properties properties) {
        if (properties == null) {
            properties = new OrderedProperties();
        }
        try {
        	OutputStream outputStream = new FileOutputStream(filename);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
          /*  prop.setProperty(key, value);  
            //保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value"); */
            properties.store(bw, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写资源文件，含中文  
     * @param filename
     * @param properties
     */
    public static void modifyPropertiesFileObj(String filename,String keyname,String keyvalue) {
        Properties properties=new Properties();
        try {
        	InputStream in = new FileInputStream(filename);
        	properties.load(in);
            properties.setProperty(keyname, keyvalue);  
            OutputStream outputStream = new FileOutputStream(filename);
            //保存，并加入注释  
            properties.store(outputStream, "Update '" + keyvalue + "' value"); 
            outputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写资源文件，含中文
     * @param filename
     * @param properties
     */
    public static void appendPropertiesFileObj(String filename,String keyname,String keyvalue) {
    	Properties properties=new OrderedProperties();
      
        try {
            OutputStream outputStream = new FileOutputStream(filename,true);
            properties.setProperty(keyname, keyvalue);   
            // 以适合使用 load 方法加载到 Properties 表中的格式，    FileOutputStream
         
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流   
            properties.store(outputStream, properties.toString());              
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
