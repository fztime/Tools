package FileUtil.File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Administrator
 *
 */
public class FileUtil {
	
	
	/**
	 * ���ļ�����
	 */
	public RandomAccessFile openFile(File file){
		RandomAccessFile raf=null;
		if(file.isFile()&&file.exists()){
			try {
				raf = new RandomAccessFile(file, "r");				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return raf;
	}
	public static void writerFileContent(String fileName,String strs){
		   try {
	            // ��һ����������ļ���������д��ʽ
	            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
	            // �ļ����ȣ��ֽ���
	            long fileLength = randomFile.length();
	            //��д�ļ�ָ���Ƶ��ļ�β��
	            randomFile.seek(fileLength);
	            String s=new String(strs.getBytes(),"UTF-8");
	            randomFile.writeBytes(strs);
	            randomFile.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	/**
	 * ȡ��ǰĿ¼��
	 * @return
	 */
	public static String getCurrentDirName(){
		return  System.getProperty("user.dir");
	}
	/**
     * �ж��ļ����Ƿ����
     * @param path �ļ���·��
     * true �ļ������ڣ�false �ļ����ڲ����κβ���
     */
    public static boolean isExist(String filePath) {
        String paths[] = filePath.split("\\\\");
        String dir = paths[0];
        for (int i = 0; i < paths.length - 2; i++) {
            try {
                dir = dir + "/" + paths[i + 1];
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("����Ŀ¼Ϊ��" + dir);
                }
            } catch (Exception err) {
                System.err.println("ELS - Chart : �ļ��д��������쳣");
            }
        }
        File fp = new File(filePath);
        if(!fp.exists()){
            return true; // �ļ������ڣ�ִ�����ع���
        }else{
            return false; // �ļ����ڲ�������
        }
    }
    public static String getFileName(String fileName){
    	return fileName.substring(fileName.lastIndexOf("\\")+1);  
    	
    }
}
