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
        System.out.println("���Ӵ�����"+count);  
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
    	  // Ϊ���Կɶ���д�ķ�ʽ���ļ�������ʹ��RandomAccessFile�������ļ���
		System.out.println("----����ʼ����----");
		Date date1 = new Date();
        FileChannel fc = new RandomAccessFile("C:\\Users\\Administrator\\Desktop\\sj\\7w.txt", "r").getChannel();  
        //ע�⣬�ļ�ͨ���Ŀɶ���дҪ�������ļ�������ɶ�д�Ļ���֮��  
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);  
        //д128M������  
        for (int i = 0; i < length; i++) {  
            out.put((byte) 'x');  
        }  
        System.out.println("Finished writing");  
        //��ȡ�ļ��м�6���ֽ�����  
        for (int i = length / 2; i < length / 2 + 6; i++) {  
            System.out.print((char) out.get(i));  
        }  
        fc.close();  
        Date date2 = new Date();
		System.out.println("----�����������----����������ʱ�䡾"
				+ (date2.getTime() - date1.getTime()) + "���롿");
	}
}
