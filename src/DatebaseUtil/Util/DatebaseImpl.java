package DatebaseUtil.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.print.attribute.standard.Finishings;
import javax.swing.JOptionPane;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.metadata.Database;
import org.junit.Test;

import StringUtil.Util.Str;

public class DatebaseImpl {
	private DatebaseDTO dto=null;
	public DatebaseImpl(){
		DatebaseDTO dto = new DatebaseDTO();
		dto.setDriver("oracle.jdbc.driver.OracleDriver");
		dto.setUrl("jdbc:oracle:thin:@192.168.0.104:1521:orcl");
		dto.setUser("system");
		dto.setPassword("aaasss");
		this.dto=dto;
	}

	/**
	 * 数据库连接 dataBase connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		DatebaseDTO dto = new DatebaseDTO();
		dto.setDriver("oracle.jdbc.driver.OracleDriver");
		dto.setUrl("jdbc:oracle:thin:@192.168.0.104:1521:orcl");
		dto.setUser("system");
		dto.setPassword("aaasss");
		Datebase db = new Datebase(dto);
		Connection conn = db.createConnectio(dto);
		if (conn == null) {
			System.out.println("数据库连接异常！");
		}
		return conn;
	}



	/**
	 * 生成表索引 create table Index
	 * 
	 * @param tableName
	 */
	public void createIndex(String tableName) {
		Datebase db = new Datebase(dto);
		Connection con = db.createConnectio(dto);
		PreparedStatement ps=null;
		if (con == null) {
			System.out.println("数据库连接异常！");
		}
		for (int i = 0; i < 26; i++) {
			StringBuffer sql = new StringBuffer();
			char c = (char) (i + 97);

			sql.append("create table " + tableName + "_" + c + "("
					+ "  id      INTEGER not null,"
					+ "  email   VARCHAR2(80) not null,"
					+ "  pwd     VARCHAR2(50) not null,"
					+ "  account VARCHAR2(50),"
					+ "  type    NUMBER(4) not null" + ")"
					+ "tablespace SYSTEM" + "  pctfree 10" + "  pctused 40"
					+ "  initrans 1" + "  maxtrans 255" + "  storage" + "  ("
					+ "    initial 64K" + "    minextents 1"
					+ "    maxextents unlimited" + "  )");
			try {
				ps= con.prepareStatement(sql.toString());
				System.out.println(sql.toString());
				ps.execute();
				ps = con.prepareStatement(sql.toString());
				sql = new StringBuffer("");
				sql.append("create index " + tableName + "_" + c + "_index on "
						+ tableName + "_" + c + " (email, pwd) ");
				sql.append(" tablespace system ");
				sql.append("  pctfree 10 ");
				sql.append("  initrans 2 ");
				sql.append(" maxtrans 255 ");
				sql.append("  storage ");
				sql.append(" ( ");
				sql.append("  initial 64K ");
				sql.append("   minextents 1 ");
				sql.append("   maxextents unlimited ");
				sql.append(" )");
				System.out.println(ps.execute());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		System.out.println("OK");
		JOptionPane.showMessageDialog(null, "OK");

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * rx create table Index
	 * 
	 * @param tableName
	 */
	public void showDateCount(String tableName) {	
		Datebase db = new Datebase(dto);
		Connection con = db.createConnectio(dto);
		PreparedStatement ps=null;
		if (con == null) {
			System.out.println("数据库连接异常！");
		}
		for (int i = 0; i < 26; i++) {
			StringBuffer sql = new StringBuffer();
			char c = (char) (i + 97);
			try {
				Date start=new Date();
				sql.append("select count(Email) count from "+tableName+"_"+c);
				ps= con.prepareStatement(sql.toString());				
				ResultSet rs=ps.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString("count"));					
				}
			rs.close();
			ps.close();
			System.out.println(tableName+"_"+c+"所用时间为："+(new Date().getTime()-start.getTime()));
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
		JOptionPane.showMessageDialog(null, "OK");	
	}
	/**
	 * 批量删除表 
	 * 
	 * @param tableName
	 */
	public void batchDelete(String tableName) {	
		Datebase db = new Datebase(dto);
		Connection con = db.createConnectio(dto);
		PreparedStatement ps=null;
		if (con == null) {
			System.out.println("数据库连接异常！");
		}
		for (int i = 0; i < 26; i++) {
			StringBuffer sql = new StringBuffer();
			char c = (char) (i + 97);
			try {
				Date start=new Date();
				sql.append("truncate table "+tableName+"_"+c);
				ps= con.prepareStatement(sql.toString());				
				System.out.println(ps.executeUpdate());
			
			ps.close();
			System.out.println(tableName+"_"+c+"所用时间为："+(new Date().getTime()-start.getTime()));
			
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
		JOptionPane.showMessageDialog(null, "OK");	
	}

	public static void main(String[] args) {
		// new DatebaseImpl().createIndex("other");
		// new DatebaseImpl().deleteTalbeImpl();
		//new DatebaseImpl().createIndex("china");
		DatebaseImpl imp=new DatebaseImpl();
		//imp.showDateCount("hotmail");
		imp.batchDelete("hotmail");
	}
}
