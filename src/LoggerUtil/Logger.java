package LoggerUtil;

import javax.swing.JOptionPane;
/**
 * 封装对话框
 * @author Administrator
 *
 */
public  class Logger{
	/**
	 * 提示对话框
	 * @param showInfo
	 */
	public static void Logger(String showInfo){
		JOptionPane.showMessageDialog(null, showInfo);
	}
}
