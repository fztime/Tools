package Project.Email;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Label;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

import DatebaseUtil.Util.Datebase;
import StringUtil.PropertiesUtil.PropertiesUtil;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.Properties;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class Email {
	private static final String DIR = System.getProperty("user.dir");
	private static final String CONFIG_NAME="config.properties" ;
	private JFrame frmEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Email window = new Email();
					window.frmEmail.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Email() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmail = new JFrame();
		frmEmail.setIconImage(Toolkit.getDefaultToolkit().getImage(Email.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmEmail.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new Datebase().releaseConnection();
				frmEmail.dispose();	
				System.exit(0);
			}
		});
		frmEmail.setTitle("Email V0.02");
		frmEmail.setBounds(100, 100, 354, 456);
		frmEmail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmail.getContentPane().setLayout(null);
		
		final Label label = new Label("");
		label.setBounds(10, 10, 343, 23);
		frmEmail.getContentPane().add(label);
		final JTextArea textArea =new TextUtil();  
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);     
		textArea.setBounds(10, 68, 322, 101);
		frmEmail.getContentPane().add(textArea);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//String Str= comboBox.getSelectedItem().toString();
				String s=(String)comboBox.getSelectedItem();
				PropertiesUtil.modifyPropertiesFileObj(PropertiesUtil.getConfigPath(), "Thread",s);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "5", "10", "20", "50", "100", "200", "500", "1000"}));
		comboBox.setEditable(true);
		comboBox.setToolTipText("");
		comboBox.setBounds(155, 396, 62, 23);
		String s=PropertiesUtil.readPropertiesFileObj(PropertiesUtil.getConfigPath()).getProperty("Thread");
		comboBox.setSelectedItem(s);
		frmEmail.getContentPane().add(comboBox);
		JButton button = new JButton("Start");
		Label label_1 = new Label("Thread\uFF1A");
		label_1.setBounds(99, 397, 51, 23);
		frmEmail.getContentPane().add(label_1);
		
		final Label lab2 = new Label("");
		lab2.setBounds(10, 39, 343, 23);
		frmEmail.getContentPane().add(lab2);
		
		final Label label3 = new Label("");
		label3.setBounds(221, 396, 43, 29);
		frmEmail.getContentPane().add(label3);
		
		final JTextArea textUtil = new TextUtil();
		textUtil.setWrapStyleWord(true);
		textUtil.setLineWrap(true);
		textUtil.setBounds(10, 176, 322, 210);
		frmEmail.getContentPane().add(textUtil);

		
		button.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				String str=textArea.getText().trim();				
				new Datebase().Start(label,lab2,label3,textUtil,str);
				
				
				//String thread=comboBox.getSelectedItem().toString();
			/*	
				
				for(int i=0;i<strs.length;i++){
					boolean flag=false;
						String path=strs[i].trim();
						
						label3.setText("");
						try {
							flag=email.Start(label,lab2,label3,path);
							System.out.println(flag);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						//System.out.println("flag:"+flag+"  I:"+i);
				
					 while (!"OK".equals(label3.getText())) {						 
					 }
					 System.out.println("OK");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
				//JOptionPane.showMessageDialog(null, "ÒÑÍê³É£¡", "Email",JOptionPane.WARNING_MESSAGE);
			}
			*/
				
			}
		});
		button.setBounds(10, 395, 73, 25);
		frmEmail.getContentPane().add(button);
		
		JButton button_1 = new JButton("Exit");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Datebase().releaseConnection();
				frmEmail.dispose();	
				System.exit(0);
			}
		});
		button_1.setBounds(270, 395, 62, 25);
		frmEmail.getContentPane().add(button_1);
		
		
		
	
	}
}
