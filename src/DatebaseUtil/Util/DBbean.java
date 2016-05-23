package DatebaseUtil.Util;

/**
 * 连接池的管理用了了享元模式，这里对连接池进行简单设计。

一、设计思路

     1.连接池配置属性DBbean：里面存放可以配置的一些属性

     2.连接池接口IConnectionPool：里面定义一些基本的获取连接的一些方法

     3.接口实现ConnectionPool：对上面操作进行实现，并加入一些其他方法

     4.连接池管理ConnectionPoolManager：管理所有的不同的连接池，所有的连接都能通过这里进行获得连接

     5.另外还有几个测试类，和连接信息模拟的类，这里就不进行xml 和配置文件信息的读取了
 */

/** 
 * 这是外部可以配置的连接池属性 
 * 可以允许外部配置，拥有默认值 
 * @author Ran 
 * 
 */  
public class DBbean {
	 // 连接池属性  
    private String driverName;  
    private String url;  
    private String userName;  
    private String password;  
    // 连接池名字  
    private String poolName;  
    private int minConnections = 1; // 空闲池，最小连接数  
    private int maxConnections = 10; // 空闲池，最大连接数  
      
    private int initConnections = 5;// 初始化连接数  
      
    private long connTimeOut = 1000;// 重复获得连接的频率  
      
    private int maxActiveConnections = 100;// 最大允许的连接数，和数据库对应  
      
    private long connectionTimeOut = 1000*60*20;// 连接超时时间，默认20分钟  
      
    private boolean isCurrentConnection = true; // 是否获得当前连接，默认true  
      
    private boolean isCheakPool = true; // 是否定时检查连接池  
    private long lazyCheck = 1000*60*60;// 延迟多少时间后开始 检查  
    private long periodCheck = 1000*60*60;// 检查频率  
      
      
      
    public DBbean(String driverName, String url, String userName,  
            String password, String poolName) {  
        super();  
        this.driverName = driverName;  
        this.url = url;  
        this.userName = userName;  
        this.password = password;  
        this.poolName = poolName;  
    }  
    public DBbean() {  
    }  
    public String getDriverName() {  
        if(driverName == null){  
            driverName = this.getDriverName()+"_"+this.getUrl();  
        }  
        return driverName;  
    }  
    public void setDriverName(String driverName) {  
        this.driverName = driverName;  
    }  
    public String getUrl() {  
        return url;  
    }  
    public void setUrl(String url) {  
        this.url = url;  
    }  
    public String getUserName() {  
        return userName;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getPoolName() {  
        return poolName;  
    }  
    public void setPoolName(String poolName) {  
        this.poolName = poolName;  
    }  
    public int getMinConnections() {  
        return minConnections;  
    }  
    public void setMinConnections(int minConnections) {  
        this.minConnections = minConnections;  
    }  
    public int getMaxConnections() {  
        return maxConnections;  
    }  
    public void setMaxConnections(int maxConnections) {  
        this.maxConnections = maxConnections;  
    }  
    public int getInitConnections() {  
        return initConnections;  
    }  
    public void setInitConnections(int initConnections) {  
        this.initConnections = initConnections;  
    }  
  
    public int getMaxActiveConnections() {  
        return maxActiveConnections;  
    }  
    public void setMaxActiveConnections(int maxActiveConnections) {  
        this.maxActiveConnections = maxActiveConnections;  
    }  
    public long getConnTimeOut() {  
        return connTimeOut;  
    }  
    public void setConnTimeOut(long connTimeOut) {  
        this.connTimeOut = connTimeOut;  
    }  
    public long getConnectionTimeOut() {  
        return connectionTimeOut;  
    }  
    public void setConnectionTimeOut(long connectionTimeOut) {  
        this.connectionTimeOut = connectionTimeOut;  
    }  
    public boolean isCurrentConnection() {  
        return isCurrentConnection;  
    }  
    public void setCurrentConnection(boolean isCurrentConnection) {  
        this.isCurrentConnection = isCurrentConnection;  
    }  
    public long getLazyCheck() {  
        return lazyCheck;  
    }  
    public void setLazyCheck(long lazyCheck) {  
        this.lazyCheck = lazyCheck;  
    }  
    public long getPeriodCheck() {  
        return periodCheck;  
    }  
    public void setPeriodCheck(long periodCheck) {  
        this.periodCheck = periodCheck;  
    }  
    public boolean isCheakPool() {  
        return isCheakPool;  
    }  
    public void setCheakPool(boolean isCheakPool) {  
        this.isCheakPool = isCheakPool;  
    }  
      
      
}
