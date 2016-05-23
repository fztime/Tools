package FileUtil.File;
import java.io.File;
import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.util.concurrent.CountDownLatch;  

public class TestThreadReadFile extends Thread{
	 //�����ֽ����飨ȡˮ����Ͳ���ĳ���    
    private final int BUFF_LEN = 256;    
    //�����ȡ����ʼ��    
    private long start;    
    //�����ȡ�Ľ�����    
    private long end;   
    //����ȡ�����ֽ������raf��  randomAccessFile�������Ϊ�ļ��������ļ�����ȡָ����һ���ֵİ�װ����  
    private RandomAccessFile raf;    
    //�߳�����Ҫָ���Ĺؼ���  
    private String keywords;  
    //���̶߳����ؼ��ֵĴ���  
    private int curCount = 0;  
    /** 
     * jdk1.5��ʼ������࣬�Ǹ����̸߳����� 
     * ���ڶ��߳̿�ʼǰͳһִ�в������߶��߳�ִ����ɺ�������߳�ִ����Ӧ�������� 
     */  
    private CountDownLatch doneSignal;  
    public TestThreadReadFile(long start, long end, RandomAccessFile raf){  
        this.start = start;  
        this.end = end;  
        this.raf  = raf;  
        this.keywords = keywords;  
        this.doneSignal = doneSignal;  
    }  
      
    public void run(){  
        try {  
            raf.seek(start);  
            //���̸߳����ȡ�ļ��Ĵ�С    
            long contentLen = end - start;    
            //���������Ҫ��ȡ���ξͿ�����ɱ��̵߳Ķ�ȡ    
            long times = contentLen / BUFF_LEN+1;    
            System.out.println(this.toString() + " ��Ҫ���Ĵ�����"+times);  
            byte[] buff = new byte[BUFF_LEN];  
            int hasRead = 0;  
            String result = null;  
            for (int i = 0; i < times; i++) {    
                //֮ǰSEEKָ������ʼλ�ã��������ָ���ֽ��鳤�ȵ����ݣ�read�������ص�����һ����ʼ����position  
                hasRead = raf.read(buff);  
                 //�����ȡ���ֽ���С��0�����˳�ѭ���� �������ֽ������ĩβ��   
                if (hasRead < 0) {    
                    break;    
                }    
                result = new String(buff,"gb2312");  
///             System.out.println(result);  
                int count = this.getCountByKeywords(result, keywords);  
                if(count > 0){  
                    this.curCount += count;  
                }  
            }  
              
            KeyWordsCount kc = KeyWordsCount.getCountObject();  
  
            kc.addCount(this.curCount);  
              
            doneSignal.countDown();//current thread finished! noted by latch object!  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
    public long getStart() {  
        return start;  
    }  
  
    public void setStart(long start) {  
        this.start = start;  
    }  
  
    public long getEnd() {  
        return end;  
    }  
  
    public void setEnd(long end) {  
        this.end = end;  
    }  
  
    public RandomAccessFile getRaf() {  
        return raf;  
    }  
  
    public void setRaf(RandomAccessFile raf) {  
        this.raf = raf;  
    }  
      
    public int getCountByKeywords(String statement,String key){  
        return statement.split(key).length-1;  
    }  
  
    public int getCurCount() {  
        return curCount;  
    }  
  
    public void setCurCount(int curCount) {  
        this.curCount = curCount;  
    }  
  
    public CountDownLatch getDoneSignal() {  
        return doneSignal;  
    }  
  
    public void setDoneSignal(CountDownLatch doneSignal) {  
        this.doneSignal = doneSignal;  
    }  
    
    
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        final int DOWN_THREAD_NUM = 10;//��10���߳�ȥ��ȡָ���ļ�  
        final String OUT_FILE_NAME = "C:\\Users\\Administrator\\Desktop\\sj\\7w.txt";  
        final String keywords = "hotmai";  
         //jdk1.5�̸߳����࣬�����̵߳ȴ��������߳�ִ����Ϻ�ʹ�õ��࣬  
        //����һ������������Լ�д��ʱ�������˽����������  
        CountDownLatch doneSignal = new CountDownLatch(DOWN_THREAD_NUM);  
        RandomAccessFile[] outArr = new RandomAccessFile[DOWN_THREAD_NUM];  
        try{  
            long length = new File(OUT_FILE_NAME).length();  
            System.out.println("�ļ��ܳ��ȣ�"+length+"�ֽ�");  
            //ÿ�߳�Ӧ�ö�ȡ���ֽ���    
            long numPerThred = length / DOWN_THREAD_NUM;    
            System.out.println("ÿ���̶߳�ȡ���ֽ�����"+numPerThred+"�ֽ�");  
          //�����ļ�������ʣ�µ�����    
            long left = length % DOWN_THREAD_NUM;  
            for (int i = 0; i < DOWN_THREAD_NUM; i++) {    
                //Ϊÿ���̴߳�һ����������һ��RandomAccessFile����    
                  
                //��ÿ���̷ֱ߳����ȡ�ļ��Ĳ�ͬ����  
                outArr[i] = new RandomAccessFile(OUT_FILE_NAME, "rw");    
                if (i != 0) {    
//    
//                    isArr[i] = new FileInputStream("d:/�¸ҵ���.rmvb");    
                    //��ָ������ļ��������RandomAccessFile����    
                      
                }    
                if (i == DOWN_THREAD_NUM - 1) {    
//                    //���һ���̶߳�ȡָ��numPerThred+left���ֽ�    
//                  System.out.println("��"+i+"���̶߳�ȡ��"+i * numPerThred+"��"+((i + 1) * numPerThred+ left)+"��λ��");  
                    new TestThreadReadFile(i * numPerThred, (i + 1) * numPerThred    
                            + left, outArr[i]).start();    
                } else {    
                    //ÿ���̸߳����ȡһ����numPerThred���ֽ�    
//                  System.out.println("��"+i+"���̶߳�ȡ��"+i * numPerThred+"��"+((i + 1) * numPerThred)+"��λ��");  
                    new TestThreadReadFile(i * numPerThred, (i + 1) * numPerThred,    
                            outArr[i]).start();    
                }    
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
//      finally{  
//            
//      }  
        //ȷ�������߳�������ɣ���ʼִ�����̵߳Ĳ���  
        try {  
            doneSignal.await();  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        //������Ҫ�����жϣ�������read�����߳�ȫ��ִ���ꡣ  
        KeyWordsCount k = KeyWordsCount.getCountObject();  
//      Map<String,Integer> resultMap = k.getMap();  
        System.out.println("ָ���ؼ��ֳ��ֵĴ�����"+k.getCount());  
    }  
}
