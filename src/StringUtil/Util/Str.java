package StringUtil.Util;

import org.junit.Test;

public class Str {
	/**
	 * ȡ�ı��ұ߽�ȡ
	 * @param src
	 * @return
	 */
	public String getRightStr(String src){
		return src.substring(src.indexOf("���:")+"���:".length());
	}
	
	@Test	
	public void testSubStr(){
		System.out.println(new Str().getRightStr("���:1.8"));
	}
}
