package DatebaseUtil.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		// 初始化连接池
		Thread t = init();
		t.start();
		t.join();
		for (int i = 0; i <2; i++) {
			ThreadConnection threadConn = new ThreadConnection();
			
			Thread thread = new Thread(threadConn);
			
			// 设置优先级，先让初始化执行，模拟 线程池 先启动
			// 这里仅仅表面控制了，因为即使t 线程先启动，也不能保证pool 初始化完成，为了简单模拟，这里先这样写了
			thread.setPriority(10);
			thread.start();
			try {
				Connection conn = threadConn.getConnection();
				System.out.println("线程-> "+conn);  
				if (conn != null) {
					StringBuffer sql = new StringBuffer();
					sql.append("select count(*) count from hotmail_a where  email=? and pwd=?");
					PreparedStatement ps = conn
							.prepareStatement(sql.toString());
					ps.setString(1, "adam122008@live.com");
					ps.setString(2, "capitalism");
					ResultSet rSet=ps.executeQuery();
					System.out.println("线程-> "+conn+" rSet:"+rSet);  
					if(rSet.next()){
						int conut=rSet.getInt("count");
						if(conut<1){
							sql=new StringBuffer();;
							sql.append("insert into hotmail_a (id,email,pwd,account,type)values(seq_email.nextval,?,?,?,?)");
							ps = conn.prepareStatement(sql.toString());
							ps.setString(1, "adam122008@live.com");
							ps.setString(2, "capitalism");
							ps.setString(3, "adam122008");
							ps.setString(4, "1");
							ps.execute();
						}
						System.out.println("rSet:"+rSet+" conut:"+conut);  
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		/*
		 * System.out.println("线程A-> "+a.getConnection());
		 * System.out.println("线程B-> "+b.getConnection());
		 * System.out.println("线程C-> "+c.getConnection());
		 */
	}

	// 初始化
	public static Thread init() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				IConnectionPool pool = initPool();
				while (pool == null || !pool.isActive()) {
					pool = initPool();
				}
			}
		});
		return t;
	}

	public static IConnectionPool initPool() {
		return ConnectionPoolManager.getInstance().getPool("testPool");
	}

}