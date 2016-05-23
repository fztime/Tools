package StringUtil.Util;

import org.junit.Test;

public class Str {
	/**
	 * »°Œƒ±æ”“±ﬂΩÿ»°
	 * @param src
	 * @return
	 */
	public String getRightStr(String src){
		return src.substring(src.indexOf("”‡∂Ó:")+"”‡∂Ó:".length());
	}
	
	@Test	
	public void testSubStr(){
		System.out.println(new Str().getRightStr("”‡∂Ó:1.8"));
	}
}
