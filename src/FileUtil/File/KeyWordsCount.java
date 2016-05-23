package FileUtil.File;
import java.io.IOException;
import java.io.RandomAccessFile;  
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel; 
import java.util.Date;

public class KeyWordsCount {
    
    private static KeyWordsCount kc;  
      
    private int count = 0;  
    private KeyWordsCount(){  
          
    }  
      
    public static synchronized KeyWordsCount getCountObject(){  
        if(kc == null){  
            kc = new KeyWordsCount();  
        }  
        return kc;  
    }  
  
    public synchronized void  addCount(int count){  
        System.out.println("增加次数："+count);  
        this.count += count;  
    }  
      
    public int getCount() {  
        return count;  
    }  
  
    public void setCount(int count) {  
        this.count = count;  
    }  
    static int length = 0x8000000; // 128 Mb  
    public static void main(String[] args) throws IOException {
    	  // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。
		System.out.println("----程序开始运行----");
		Date date1 = new Date();
        FileChannel fc = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\sj\\7w.txt", "r").getChannel();  
        //注意，文件通道的可读可写要建立在文件流本身可读写的基础之上  
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);  
        //写128M的内容  
        for (int i = 0; i < length; i++) {  
            out.put((byte) 'x');  
        }  
        System.out.println("Finished writing");  
        //读取文件中间6个字节内容  
        for (int i = length / 2; i < length / 2 + 6; i++) {  
            System.out.print((char) out.get(i));  
        }  
        fc.close();  
        Date date2 = new Date();
		System.out.println("----程序结束运行----，程序运行时间【"
				+ (date2.getTime() - date1.getTime()) + "毫秒】");
	}
}
