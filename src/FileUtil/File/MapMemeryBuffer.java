package FileUtil.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapMemeryBuffer {
	 private static String fileName = "C:\\Users\\Administrator\\Desktop\\sj\\1.txt";  
	  
	    private static String fileName1 = "C:\\Users\\Administrator\\Desktop\\sj\\1.txt";  
	  
	    public static void main(String[] args) throws Exception {  
	        mapBufferWriteFile();  
	    }  
	  
	    public static long length;  
	  
	    public static ByteBuffer mapBufferReadFile() {  
	        try {  
	            RandomAccessFile fis = new RandomAccessFile(fileName, "r");  
	            FileChannel fc = fis.getChannel();  
	            length = fc.size();  
	            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0,  
	                length);  
	            System.out.println(new String(new byte[(int) fc.size()]));
	            return mbb.get(new byte[(int) fc.size()]);  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	  
	    }  
	  
	    public static void mapBufferWriteFile() {  
	        try {  
	            long start = System.currentTimeMillis();  
	            // FileOutputStream fos = new FileOutputStream(fileName1);  
	            RandomAccessFile fos = new RandomAccessFile(fileName1, "rw");  
	            FileChannel fc = fos.getChannel();  
	            ByteBuffer bb = mapBufferReadFile();  
	            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0,  
	                length);  
	            for (int i = 0; i < length; i++) {  
	                //mbb.put(i, bb.get(i)); 
	            	 //byte t=bb.get();
	            	 
	            	System.out.println(bb.get(i));
	            }  
	            //mbb.flip();  
	            System.out.println(System.currentTimeMillis() - start);  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
