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
	 * ȡ���ݿ�����
	 * @return
	 */
	public static DatebaseDTO readPorperties() {
		String path = DIR + "\\" + DBCONFIG;
		File file = new File(path);
		if (!file.isFile() && !file.exists()) {
			new Logger().Logger("�ļ�������߲����ڣ�");
		}
		return readPropertiesFile(path);

	}
	public static String getConfigPath(){
		return DIR+"\\"+CONFIG;
	}

	/**
	 * �����ݿ�������ļ�
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
			inputStream.close(); // �ر���
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dbConfigDTO;
	}
    /**
     * ��ȡ��Դ�ļ�,��������������
     * @param filename
     * @return
     */
    public static Properties readPropertiesFileObj(String filename) {
        Properties properties = new OrderedProperties();
        try {
            InputStream inputStream = new FileInputStream(filename);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            properties.load(bf);
            inputStream.close(); // �ر���
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    
    /**
     * д��Դ�ļ���������
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
            //���棬������ע��  
            prop.store(fos, "Update '" + key + "' value"); */
            properties.store(bw, null);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * д��Դ�ļ���������  
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
            //���棬������ע��  
            properties.store(outputStream, "Update '" + keyvalue + "' value"); 
            outputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * д��Դ�ļ���������
     * @param filename
     * @param properties
     */
    public static void appendPropertiesFileObj(String filename,String keyname,String keyvalue) {
    	Properties properties=new OrderedProperties();
      
        try {
            OutputStream outputStream = new FileOutputStream(filename,true);
            properties.setProperty(keyname, keyvalue);   
            // ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��    FileOutputStream
         
            // ���� Properties ���е������б�����Ԫ�ضԣ�д�������   
            properties.store(outputStream, properties.toString());              
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
