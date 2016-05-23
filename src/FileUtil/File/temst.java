package FileUtil.File;
import java.io.IOException;
import java.io.RandomAccessFile;  
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel;  
import java.util.Date;


public class temst {
    static int length = 0x8000000; // 128 Mb  
    
    public static void main(String[] args) throws Exception {  
        // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。  
    	ByteBuffer byteBuf = ByteBuffer.allocate(1024 * 14 * 1024);
		System.out.println("----程序开始运行----");
		Date date1 = new Date();		
		int length=0;  
		int n=0;
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\sj\\3.txt", "r");  
        while(raf.read()!=-1){
        	n++;
        	String str=raf.readLine();
        	//String str=getline(raf);
        	length=length+str.length();
        	raf.seek(length+n*2);
        	System.out.println(str+" length:"+length);
        	
		}
        Date date2 = new Date();
  		System.out.println("----程序结束运行----，程序运行时间【"
  				+ (date2.getTime() - date1.getTime()) + "毫秒】");
      
    } 
    public static String getline(RandomAccessFile raf) throws IOException{
    	String str=raf.readLine();
    	return str;
    }
}
