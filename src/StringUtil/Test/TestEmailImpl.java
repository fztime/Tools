package StringUtil.Test;

import org.junit.Test;

import StringUtil.Entity.EmailImpl;


public class TestEmailImpl {
	EmailImpl impl=new EmailImpl();
	
	@Test
	public void testDoFourLetter(){
		String str="ewe";
		int l=str.length();
		String s=impl.doFourLetter(str, l);
		System.out.println(s);
	}
}
