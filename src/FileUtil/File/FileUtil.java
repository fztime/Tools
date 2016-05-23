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
	 * 读文件内容
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
	            // 打开一个随机访问文件流，按读写方式
	            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
	            // 文件长度，字节数
	            long fileLength = randomFile.length();
	            //将写文件指针移到文件尾。
	            randomFile.seek(fileLength);
	            String s=new String(strs.getBytes(),"UTF-8");
	            randomFile.writeBytes(strs);
	            randomFile.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	/**
	 * 取当前目录名
	 * @return
	 */
	public static String getCurrentDirName(){
		return  System.getProperty("user.dir");
	}
	/**
     * 判断文件夹是否存在
     * @param path 文件夹路径
     * true 文件不存在，false 文件存在不做任何操作
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
                    System.out.println("创建目录为：" + dir);
                }
            } catch (Exception err) {
                System.err.println("ELS - Chart : 文件夹创建发生异常");
            }
        }
        File fp = new File(filePath);
        if(!fp.exists()){
            return true; // 文件不存在，执行下载功能
        }else{
            return false; // 文件存在不做处理
        }
    }
    public static String getFileName(String fileName){
    	return fileName.substring(fileName.lastIndexOf("\\")+1);  
    	
    }
}
