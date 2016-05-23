package DatebaseUtil.Util;

import java.sql.Connection;  
/** 
 * ģ���߳�������ȥ������� 
 * @author Ran 
 * 
 */  
public class ThreadConnection implements Runnable{  
    private IConnectionPool pool;  
    @Override  
    public void run() {  
        pool = ConnectionPoolManager.getInstance().getPool("testPool");  
    /*    System.out.println("pool:"+pool);
        System.out.println("pool--:"+pool.isActive());*/
    }  
      
    public Connection getConnection(){  
        Connection conn = null;  
        System.out.println("pool:"+pool+" --");
        if(pool != null && pool.isActive()){  
        	conn = pool.getConnection();  
            System.out.println("conn:"+conn);
        } 
        return conn;  
    }  
      
    public Connection getCurrentConnection(){  
        Connection conn = null;  
        if(pool != null && pool.isActive()){  
            conn = pool.getCurrentConnecton();  
        }  
        return conn;  
    }  
}  