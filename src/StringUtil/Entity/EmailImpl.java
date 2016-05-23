package StringUtil.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailImpl {
	/**
	 * 处理数据 分割数据
	 * 
	 * @param args
	 */
	public EmailDTO splitData(String str) {
		String[] strs = null;
		strs = str.trim().split("\\t");
		/*
		 * if(str.indexOf("\\t")>0){
		 * 
		 * 
		 * }else if(str.indexOf("\\s*")>0){
		 * 
		 * }else if(str.indexOf("----")>0){ strs=str.split("----"); }else {
		 * return null; }
		 */
		if (strs.length >= 2) {
			EmailDTO dto = new EmailDTO();
			String email = strs[0];
			String pwd = strs[1];
			dto.setEmail(email.toLowerCase().trim());
			dto.setPassword(pwd.trim());
			EmailDTO newdto = checkFormat(dto);
			newdto=getAccountByEmail(newdto);
			return newdto;
		} else {
			return null;
		}
	}
	/**
	 * 检测格式分类
	 * 
	 * @param dto
	 */
	public EmailDTO checkFormat(EmailDTO dto) {
		if (isEmail(dto.getEmail())) {
			Integer type = sortEmail(dto.getEmail());
			dto.setType(type);
		} else {
			dto.setType(0);
		}
		return dto;
	}
	/**
	 * 判断是否是邮箱 isEmail
	 * 
	 * @param str
	 * @return
	 */
	public boolean isEmail(String str) {
		//String reg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		String reg = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.{0,3}[a-zA-Z]+\\s*$";
		Pattern regex = Pattern.compile(reg);
		Matcher matcher = regex.matcher(str);
		boolean flag = matcher.matches();
		if (flag) {
			return true;
		}
		return false;
	}

	/**
	 * 邮箱分类
	 * 
	 * @param email
	 * @return
	 */
	public Integer sortEmail(String email) {
		long start=System.currentTimeMillis();
		Integer n = checkEmailWithSuffix(email);
		//System.out.println("分类："+(System.currentTimeMillis()-start));
		return n;
	}
	/**
 	 * * 检查EMAIL地址 	     
 	 * @param email 用户名和网站名称必须>=1位字符 
 	 * @param regular  邮箱必须是以163|gmail|sina 
 	 * @return
 	 */
    public  Integer checkEmailWithSuffix(String email)  
    {  
    	String regex=".*@(hotmail|msn|live)(\\.+\\w+)+";
        //String regex="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@(hotmail|msn|live)(\\.+\\w+)+";  
        if(startCheck(regex,email)){
        	return 1;
        }
       regex=".*@(gmail|google)(\\.+\\w+)+";    
        if(startCheck(regex,email)){
        	return 2;
        }
        regex=".*@(yahoo)(\\.+\\w+)+";  
        if(startCheck(regex,email)){
        	return 3;
        }
        regex=".*@(qq|163|126)(\\.+\\w+)+";  
        if(startCheck(regex,email)){
        	return 4;
        }
        regex=".*@\\s*\\w+(\\.+\\w+)+";  
        if(startCheck(regex,email)){
        	return 5;
        }
        
        return 0;  
    } 
    public  boolean startCheck(String reg,String string)  
    {  
        boolean tem=false;  
          
        Pattern pattern = Pattern.compile(reg);  
        Matcher matcher=pattern.matcher(string);  
          
        tem=matcher.matches();  
        return tem;  
    }
    public String getType(EmailDTO dto){
    	int type=dto.getType();
		String table="";
		switch (type) {
		case 1:
			table = "hotmail";
			break;
		case 2:
			table = "google";
			break;
		case 3:
			table = "yahoo";
			break;
		case 4:
			table = "china";
			break;
		case 5:
			table = "other";
			break;
		default:
			table = "emailError";
			break;
		}
		return table;
    }
    
    /**
     * 设置表名  根据邮箱设置表名
     * @param emailDTO
     * @return
     */
    public EmailDTO setTableNameByType(EmailDTO emailDTO){
		String frist=emailDTO.getEmail().substring(0,1).toLowerCase();
		String  table="";
		//邮箱和密码大于50 当error邮箱
		if(emailDTO.getEmail().length()>50||emailDTO.getPassword().length()>50){
			 table="emailError";
		}else{
			 table=getType(emailDTO);
		}
		String tableName=table+"_"+frist;
		if("emailError".equals(table)){
			tableName=table;
		}else{
			tableName=table+"_"+getFourLetter(emailDTO.getAccount());
			/*int cha=(int)frist.charAt(0);
			if(cha<97||cha>122){
				tableName=table+"_"+0;
			}*/
		}
		//处理密码有单引号
		String pwd=emailDTO.getPassword();
		pwd = pwd.replaceAll("'", "''");
		emailDTO.setPassword(pwd);
		String email=emailDTO.getEmail();
		email=email.replace("'", "''");
		emailDTO.setAccount(emailDTO.getAccount().replace("'", "''"));
		emailDTO.setEmail(email);
		emailDTO.setTableNmae(tableName);
		return emailDTO;
    }
    /**
     * 取字符串首字母,非字母为0
     * @param str
     * @return
     */
    public String getFirstType(String str){
    	int cha=(int)str.charAt(0);
    	if(cha<97||cha>122){    		
    		return "0";
    	}
    	return String.valueOf(str.charAt(0));
    }
    /**
     * 取邮箱和密码的首字母做为类型,用作分类
     * @param emailDto
     * @return
     */
    public String getEmailAndPwdFirst(EmailDTO emailDto){
    	String emailfirst=getFirstType(emailDto.getEmail().trim().toLowerCase());
    	String passwordfirst=getFirstType(emailDto.getPassword().toLowerCase());
    	return emailfirst+"_"+passwordfirst;
    }
    
    /**
     * 根据邮箱取账号
     * @param dto
     * @return
     */
    public EmailDTO getAccountByEmail(EmailDTO dto){
    	String email=dto.getEmail();
    	String account=email.substring(0, email.indexOf("@"));
    	dto.setAccount(account);
    	return dto;
    }
    /**
     * 取字符串4位,非字母为0000
     * @param tableName 
     * @return
     */
    public String getFourLetter(String str) {
		String tableIndex="";
		int l = str.length();
		switch (l) {
		case 1:
			tableIndex=doFourLetter(str,l);
		case 2:
			tableIndex=doFourLetter(str,l);
		case 3:
			tableIndex=doFourLetter(str,l);
		default:
			if (str.length() >= 4) {
				tableIndex=doFourLetter(str,l);
			}
		}
		return tableIndex;
	}
    /**
     * 取前4位,非字母为0000
     * @param account
     * @param l
     * @return
     */
	public String doFourLetter(String str,int l){
		if(l>4){
			l=4;
		}
		String s = str.substring(0, l);
		boolean flag = s.matches("[a-zA-Z]{" + l + "}");
		if (flag) {
			return s;
		} 
		s="";
		for (int i = 0; i < l; i++) {
			s=s+"0";
		}
		return s;
	}
	
	/**
     * 判断文件夹是否存在
     * @param path 文件夹路径
     * true 文件不存在，false 文件存在不做任何操作
     */
    public  boolean isExist(String filePath) {
        String paths[] = filePath.split("\\\\");
        String dir = paths[0];
        for (int i = 0; i < paths.length - 2; i++) {
            try {
                dir = dir + "/" + paths[i + 1];
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("创建目录为：" + dir);
                }
            } catch (Exception err) {
                System.err.println("ELS - Chart : 文件夹创建发生异常");
            }
        }
        File fp = new File(filePath);
        if(!fp.exists()){
            return true; // 文件不存在，执行下载功能
        }else{
            return false; // 文件存在不做处理
        }
    }
    public Integer queryRepeatEmail(File file,String str){
		 final int BUFFER_SIZE = 0x300000;// 缓冲区大小为3M         
	   
	        MappedByteBuffer inputBuffer = null;
			try {
				inputBuffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY,
						file.length() / 2, file.length() / 2);
				 byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容
				 
			        long start = System.currentTimeMillis();
			 
			        for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
			 
			            if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
			 
			                for (int i = 0; i < BUFFER_SIZE; i++)
			 
			                    dst[i] = inputBuffer.get(offset + i);
			 
			            } else {
			 
			                for (int i = 0; i < inputBuffer.capacity() - offset; i++)
			 
			                    dst[i] = inputBuffer.get(offset + i);
			 
			            }
			 
			            int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
			                         : inputBuffer.capacity() % BUFFER_SIZE;
			            if(str.equals(new String(dst, 0, length))){
			            	return 1;
			            }
			 
			            System.out.println(new String(dst, 0, length));//
			 
			        }
			        inputBuffer.clear();
			        long end = System.currentTimeMillis();
			        
			        System.out.println("读取文件文件一半内容花费：" + (end - start) + "毫秒");
			        
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return 0;	
		 
	}
	
}
