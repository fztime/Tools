package Project.Email;


public class test {
	public static void main(String[] args) {
		String e = "dswq@hotmail.com";
		System.out.println(new test().setTable(e));
	}

	public String setTable(String tableName) {
		String tableIndex="";
		String account = tableName.substring(0, tableName.indexOf("@"));
		int l = account.length();
		switch (l) {
		case 1:
			tableIndex=doGetTable(account,l);
		case 2:
			tableIndex=doGetTable(account,l);
		case 3:
			tableIndex=doGetTable(account,l);
		default:
			if (account.length() >= 4) {
				tableIndex=doGetTable(account,l);
			}
		}
		return tableIndex;
	}
	public String doGetTable(String account,int l){
		if(l>4){
			l=4;
		}
		String s = account.substring(0, l);
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
	
}
