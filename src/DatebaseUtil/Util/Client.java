package DatebaseUtil.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		// ��ʼ�����ӳ�
		Thread t = init();
		t.start();
		t.join();
		for (int i = 0; i <2; i++) {
			ThreadConnection threadConn = new ThreadConnection();
			
			Thread thread = new Thread(threadConn);
			
			// �������ȼ������ó�ʼ��ִ�У�ģ�� �̳߳� ������
			// ���������������ˣ���Ϊ��ʹt �߳���������Ҳ���ܱ�֤pool ��ʼ����ɣ�Ϊ�˼�ģ�⣬����������д��
			thread.setPriority(10);
			thread.start();
			try {
				Connection conn = threadConn.getConnection();
				System.out.println("�߳�-> "+conn);  
				if (conn != null) {
					StringBuffer sql = new StringBuffer();
					sql.append("select count(*) count from hotmail_a where  email=? and pwd=?");
					PreparedStatement ps = conn
							.prepareStatement(sql.toString());
					ps.setString(1, "adam122008@live.com");
					ps.setString(2, "capitalism");
					ResultSet rSet=ps.executeQuery();
					System.out.println("�߳�-> "+conn+" rSet:"+rSet);  
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
		 * System.out.println("�߳�A-> "+a.getConnection());
		 * System.out.println("�߳�B-> "+b.getConnection());
		 * System.out.println("�߳�C-> "+c.getConnection());
		 */
	}

	// ��ʼ��
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