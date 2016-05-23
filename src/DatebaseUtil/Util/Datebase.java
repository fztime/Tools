package DatebaseUtil.Util;

import java.awt.Label;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.zip.CRC32;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.WrappedPlainView;
import javax.xml.stream.events.StartDocument;

import org.junit.experimental.theories.FromDataPoints;

import FileUtil.File.FileUtil;
import LoggerUtil.Logger;
import StringUtil.Entity.EmailDTO;
import StringUtil.Entity.EmailImpl;
import StringUtil.PropertiesUtil.PropertiesUtil;
import StringUtil.Util.Str;

/**
 * 数据库方法
 * 
 * @author jobscn
 * @2016-4-28
 * 
 */
/**
 * @author Administrator
 * 
 */
public class Datebase {
	
	private Connection conn = null;
	private Long length = 0L;
	private int repeatNum = 0;
	private Long n = 0L;
	private Properties pt = new Properties();
	private String pathFileName;
	private int num;
	private boolean isExit=false;
	boolean flag = false;
	private String fName;
	private String path="";
	private RandomAccessFile buffer =null;
	private Label  lineNumberLable= null;
	private Label repeatNumLable = null;
	private Label lab3 = null;
	private String filePath;

	public String getPathFileName() {
		return pathFileName;
	}

	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	public Datebase() {

	}

	public Datebase(DatebaseDTO dto) {
		this.conn = createConnectio(dto);

	}

	/**
	 * 数据库创建连接
	 * 
	 * @param dto
	 * @return
	 */
	public Connection createConnectio(DatebaseDTO dto) {
		try {
			Class.forName(dto.getDriver());
			conn = DriverManager.getConnection(dto.getUrl(), dto.getUser(),
					dto.getPassword());
		} catch (SQLException e) {
			conn = null;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			conn = null;
			e.printStackTrace();
		}
		return conn;
	}

	public void releaseConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param dto
	 * @param sql
	 * @return
	 */
	public ResultSet execute(StringBuffer sql) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;

	}

	/**
	 * 执行带map参数的sql语句
	 * 
	 * @param dto
	 * @param sql
	 * @param map
	 * @return
	 */
	public ResultSet execute(StringBuffer sql, Map map) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			Iterator entries = map.entrySet().iterator();
			Integer i = 0;
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				// System.out.println("Key = " + key + ", Value = " + value);
				i++;
				ps.setString(i, value);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	/**
	 * 
	 */
	public void deleteTableImpl() {
		DatebaseDTO dto = new DatebaseDTO();
		dto.setDriver("oracle.jdbc.driver.OracleDriver");
		dto.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		dto.setUser("system");
		dto.setPassword("aaasss");
		Datebase db = new Datebase(dto);
		if (db.conn == null) {
			// System.out.println("数据库连接异常！");
		}
		for (int i = 0; i < 26; i++) {
			StringBuffer sql = new StringBuffer();
			// sql.append("select count(*) from hotmail_d");
			char c = (char) (i + 97);
			sql.append("create index hotmail_" + c + "_index on hotmail_" + c
					+ " (email, pwd) ");
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
			db.execute(sql);
		}
		// System.out.println("OK");
		JOptionPane.showMessageDialog(null, "OK");

		db.releaseConnection();
	}

	/**
	 * 测试批量创建 索引
	 */
	public void Testdb() {
		DatebaseDTO dto = new DatebaseDTO();
		dto.setDriver("oracle.jdbc.driver.OracleDriver");
		dto.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
		dto.setUser("system");
		dto.setPassword("aaasss");
		Datebase db = new Datebase(dto);
		if (db.conn == null) {
			// System.out.println("数据库连接异常！");
		}
		for (int i = 0; i < 26; i++) {
			StringBuffer sql = new StringBuffer();
			// sql.append("select count(*) from hotmail_d");
			char c = (char) (i + 97);
			sql.append("create index other_" + c + "_index on other__" + c
					+ " (email, pwd) ");
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
			db.execute(sql);
		}
		// System.out.println("OK");
		JOptionPane.showMessageDialog(null, "OK");

		db.releaseConnection();
	}

	public static void main(String[] args) {
		
		/*
		 * Properties ps=new Properties(); String
		 * pathString="C:\\Users\\Administrator\\Desktop\\sj\\1.txt";
		 * ps.setProperty("C:\\Users\\Administrator\\Desktop\\sj\\1.txt",
		 * "0|0");
		 * ps.setProperty("C:\\Users\\Administrator\\Desktop\\sj\\2.txt",
		 * "0|0"); PropertiesUtil.writePropertiesFileObj(
		 * "D:\\Works\\workspace\\Eclipse\\Tools\\config.properties",ps);
		 */
		//new Datebase().Testdb();
		//new Datebase().testfile();
	}

	

	public String geteamil(BufferedReader rf, String line, int index,
			String path) throws IOException {
		n++;
		String emailStr = line;
		length = length + emailStr.length();
		rf.skip(length + n * 2);
		// rf.seek(length + n * 2);\\
		PropertiesUtil.modifyPropertiesFileObj(PropertiesUtil.getConfigPath(),
				path, n + "," + length);
		System.out.println(emailStr + " sum:" + n + " length:" + length);

		return emailStr;
	}

	public ResultSet executeCreate(EmailDTO dto) {
		StringBuffer sql = new StringBuffer();
		String tableName = dto.getTableNmae();
		String email = dto.getEmail();
		String account = dto.getAccount();
		String password = dto.getPassword();
		Integer type = dto.getType();
		sql.append("insert into " + tableName
				+ "(id,email,pwd,account,type) values (seq_email.nextval,'"
				+ email + "','" + password + "','" + account + "','"
				+ type.toString() + "')");
		return execute(sql);
	}

	public static Properties getConfig(String path) {
		return PropertiesUtil.readPropertiesFileObj(path);
	}
	public  String getLine(RandomAccessFile buffer) throws IOException{
			n++;				
			String line=buffer.readLine();
			if(line!=null){
				System.out.println("length:"+length +" line:"+line);
				length = length + line.length() + 2;// +2换行
				lineNumberLable.setText("File:"+fName+"    LineNumber:"+n);	
				PropertiesUtil.modifyPropertiesFileObj(filePath,path, n + "," + length);
			}else {
				length=0L;
			}
			return buffer.readLine();	
	}

	public void Start(Label label, Label label2, Label label3,final JTextArea ta,
			final String content)  {
		
		lineNumberLable = label;
		repeatNumLable=label2;
		final Label lab3 = label3;
		filePath = PropertiesUtil.getConfigPath();
		final String DIR = System.getProperty("user.dir");		
		String[] temps=content.split("\r\n");
		DatebaseDTO dto = PropertiesUtil.readPorperties();
		final Datebase dbUtil = new Datebase(dto);
		Properties pt = PropertiesUtil.readPropertiesFileObj(PropertiesUtil.getConfigPath());
		
		String threadStr = (String) pt.get("Thread");
		Integer threadnum = 0;
		if (threadStr != null) {
			threadnum = Integer.parseInt(threadStr);
		}
		Integer taskSize = threadnum;
		//创建线程池
		final ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		for (int ii = 0; ii < temps.length; ii++) {
			isExit=false;
			path=temps[ii].trim();
			fName=new FileUtil().getFileName(path);			
			String nums = (String) pt.get(path);
			if (nums != null || "".equals(nums)) {
				String[] strs = nums.split(",");
				n = Long.valueOf(strs[0]);
				length = Long.valueOf(strs[1]) + 2;//注意：这里加2文本行换行所占字符
				
			} else {
				PropertiesUtil.appendPropertiesFileObj(
						PropertiesUtil.getConfigPath(), path, "0,0");
				length = 0L;
				n = 0L;
			}
			try {
				buffer = new RandomAccessFile(new File(path), "r");		
				buffer.seek(length);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < taskSize; i++) {
				final int index = i;				
				pool.execute(new Runnable() {
					@Override
					public void run() {
							EmailDTO emailDTO=new EmailDTO();
							EmailImpl impl = new EmailImpl();
							String line="";
							while (true) {
								PreparedStatement ps=null;
								try {
									line=getLine(buffer);
									if(line!=null){
										emailDTO = impl.splitData(line);
										ta.append("email:"+ line);
										emailDTO=impl.getAccountByEmail(emailDTO);
										String sql="insert into "+impl.getType(emailDTO)+" (id,email,pwd,account,type) values (seq_email.nextval,?,?,?,?)";
										ps=dbUtil.conn.prepareStatement(sql);
										ps.setString(1, emailDTO.getEmail());
										ps.setString(2, emailDTO.getPassword());
										ps.setString(3, emailDTO.getAccount());
										ps.setString(4, String.valueOf(emailDTO.getType()));
										ps.execute();
									}
									ta.append("databases:"+emailDTO.getEmail());
									//System.out.println("index:"+index+"  n:"+n);
									if(line==null){
										break;
									}
								}catch(SQLIntegrityConstraintViolationException e){
									repeatNum++;
									repeatNumLable.setText("RepeatEmail:"+repeatNum);
								}catch (IOException e) {
									e.printStackTrace();
									ta.append(e.toString());
								} catch (SQLException e) {
									e.printStackTrace();
									ta.append(e.toString());
								}finally {
									if(ps!=null){
										try {
											ps.close();
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
								}
								
							}
						isExit=true;
						System.out.println("isExit"+isExit);
					}
				});	
				while (!isExit) {
					
				}
				
			}	
		}
		//pool.shutdown();
		//dbUtil.releaseConnection();
		JOptionPane.showMessageDialog(null, "已完成！", "Email",JOptionPane.WARNING_MESSAGE);
	}
	

	
	public void addToDatabases(Datebase dbUtil,EmailDTO emailDTO,EmailImpl impl) throws SQLException{
		String sql="insert into "+impl.getType(emailDTO)+" (id,email,pwd,account,type) values (seq_email.nextval,?,?,?,?)";
		//System.out.println(sql);		
			PreparedStatement ps = dbUtil.conn.prepareStatement(sql);
			ps.setString(1, emailDTO.getEmail());
			ps.setString(2, emailDTO.getPassword());
			ps.setString(3, emailDTO.getAccount());
			ps.setString(4, String.valueOf(emailDTO.getType()));
			ResultSet rs = ps.executeQuery();
			rs.close();
			ps.close();
			
	}
	/**
	 * 删重复邮箱
	 * @param buff
	 * @param emailDTO
	 * @param impl
	 */
	public synchronized String delRepeatEmail(String filePath,String path,RandomAccessFile buff,Label lab){
		String emailStr="";
		String line="";
		try {
			if ((line = buff.readLine()) != null) {	
				num++;
				if(num>n){
					emailStr= line;					
					n++;
					PropertiesUtil.modifyPropertiesFileObj(filePath,path, n + "," + length);
					lab.setText("File:"+fName+"    LineNumber:"+n);
				}
				/*String emailAndPwdFirst=impl.getEmailAndPwdFirst(emailDTO);
				String emailLength=String.valueOf(emailDTO.getAccount().length());
				String fourLetter=impl.getFourLetter(emailDTO.getAccount());
				String fileName=currentDirName+File.separator+"EmailDir"+File.separator+impl.getType(emailDTO)+File.separator
						+emailLength+File.separator+emailAndPwdFirst+File.separator+fourLetter+".txt";
				File file=new File(fileName);
				Integer ifRepeat = 0;
				if(!file .exists()){
					if(impl.isExist(fileName)){
						emailDTO.setStatus(1);
					}
				}else{
					ifRepeat=queryRepeatEmail(file,emailDTO.getEmail());
				}
				emailDTO.setStatus(ifRepeat);*/	
					
			}else{
				flag=true;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailStr;
	}

	public static String getFileNameByPathName(String PathName) {
		return PathName.trim().substring(PathName.trim().lastIndexOf("\\") + 1);
	}
	
}
