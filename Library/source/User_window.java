package Library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Library.My_status.Show;
import Library.My_status.fileWrite;
import Library.My_status.Show.MyPanel;
import Library.Search.bookData;


public class User_window extends JFrame implements ActionListener{
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JButton search, now, inquire, logout, status;
	JButton ¶ó¼î¸ù, ¹é¹üÀÏÁö, ÀÌ±âÀûÀÎÀ¯ÀüÀÚ, ÀÚº»ÁÖÀÇ4_0, Á×ÀÌ´ÂÃ¥, ÃÑ±Õ¼è;
	JLabel book;
	
	String stu_num = LB_login.stu_number;
	Font LB_font = new Font("HY±Ã¼­B",Font.BOLD,32);
	User_window(){
		setTitle("User_Login");
		setSize(1280,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width)/2,(windowSize.height - frameSize.height)/2);
		
		//layout setting
		setLayout(null);
		layeredPan = new JLayeredPane();
		layeredPan.setBounds(0,0,1280,750);
		layeredPan.setLayout(null);
		
		try {
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ad¹è°æ.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		search = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_search.png"));
		search.setBounds(100, 150, 200, 100);
		search.addActionListener(this);
		
		search.setBorderPainted(false);
		search.setFocusPainted(false);
		search.setContentAreaFilled(false);
		
		book = new JLabel("ÀÌ´ÞÀÇ º£½ºÆ®¼¿·¯");
		book.setFont(LB_font);
		book.setBounds(700, 50, 300, 50);
		
		logout = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_logout.png"));
		logout.setBounds(1150,10,100,50);
		logout.addActionListener(this);
		
		logout.setBorderPainted(false);
		logout.setFocusPainted(false);
		logout.setContentAreaFilled(false);
		
		status = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_status.png"));
		status.setBounds(100,330,200,100);
		status.addActionListener(this);
		
		status.setBorderPainted(false);
		status.setFocusPainted(false);
		status.setContentAreaFilled(false);
		
		inquire = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_inquire.png"));
		inquire.setBounds(100,500,200,100);
		inquire.addActionListener(this);
		
		inquire.setBorderPainted(false);
		inquire.setFocusPainted(false);
		inquire.setContentAreaFilled(false);
		
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,660,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		//¶ó¼î¸ù, ¹é¹üÀÏÁö, ÀÌ±âÀûÀÎÀ¯ÀüÀÚ, ÀÚº»ÁÖÀÇ4_0, Á×ÀÌ´ÂÃ¥, ÃÑ±Õ¼è, ÅäÁö;
		
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ÀÌ±âÀûÀÎ.png"));
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ.setBounds(520,120,200,250);
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ.addActionListener(this);
	
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ.setBorderPainted(false);
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ.setFocusPainted(false);
		ÀÌ±âÀûÀÎÀ¯ÀüÀÚ.setContentAreaFilled(false);
		
		¶ó¼î¸ù = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/¶ó¼î¸ù.png"));
		¶ó¼î¸ù.setBounds(750,120,200,250);
		¶ó¼î¸ù.addActionListener(this);
	
		¶ó¼î¸ù.setBorderPainted(false);
		¶ó¼î¸ù.setFocusPainted(false);
		¶ó¼î¸ù.setContentAreaFilled(false);
		
		¹é¹üÀÏÁö = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/¹é¹üÀÏÁö.png"));
		¹é¹üÀÏÁö.setBounds(980,120,200,250);
		¹é¹üÀÏÁö.addActionListener(this);
	
		¹é¹üÀÏÁö.setBorderPainted(false);
		¹é¹üÀÏÁö.setFocusPainted(false);
		¹é¹üÀÏÁö.setContentAreaFilled(false);
		
		ÀÚº»ÁÖÀÇ4_0 = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ÀÚº»ÁÖÀÇ.png"));
		ÀÚº»ÁÖÀÇ4_0.setBounds(520,400,200,250);
		ÀÚº»ÁÖÀÇ4_0.addActionListener(this);
	
		ÀÚº»ÁÖÀÇ4_0.setBorderPainted(false);
		ÀÚº»ÁÖÀÇ4_0.setFocusPainted(false);
		ÀÚº»ÁÖÀÇ4_0.setContentAreaFilled(false);
		
		Á×ÀÌ´ÂÃ¥ = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Á×ÀÌ´Â.png"));
		Á×ÀÌ´ÂÃ¥.setBounds(750,400,200,250);
		Á×ÀÌ´ÂÃ¥.addActionListener(this);
	
		Á×ÀÌ´ÂÃ¥.setBorderPainted(false);
		Á×ÀÌ´ÂÃ¥.setFocusPainted(false);
		Á×ÀÌ´ÂÃ¥.setContentAreaFilled(false);
		
		ÃÑ±Õ¼è = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ÃÑ±Õ¼è.png"));
		ÃÑ±Õ¼è.setBounds(980,400,200,250);
		ÃÑ±Õ¼è.addActionListener(this);
	
		ÃÑ±Õ¼è.setBorderPainted(false);
		ÃÑ±Õ¼è.setFocusPainted(false);
		ÃÑ±Õ¼è.setContentAreaFilled(false);
		
		layeredPan.add(ÃÑ±Õ¼è);
		layeredPan.add(Á×ÀÌ´ÂÃ¥);
		layeredPan.add(ÀÚº»ÁÖÀÇ4_0);
		layeredPan.add(¹é¹üÀÏÁö);
		layeredPan.add(¶ó¼î¸ù);
		layeredPan.add(ÀÌ±âÀûÀÎÀ¯ÀüÀÚ);
		layeredPan.add(clockMessage);
		layeredPan.add(inquire);
		layeredPan.add(status);
		layeredPan.add(logout);
		layeredPan.add(book);
		//layeredPan.add(monthlyBook);
		layeredPan.add(search);
		layeredPan.add(pn);
		add(layeredPan);
		setUndecorated(true);
		setVisible(true);
	}
	class MyPanel extends JPanel{
		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
	public static void main() {
		// TODO Auto-generated method stub
		new User_window();
	}
	class ClockMessage extends JPanel implements Runnable{
		int i = Calendar.getInstance().get(Calendar.AM_PM);
		String[] amPm = {"AM", "PM"};
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		String time = sdf.format(new Date());
		JLabel timeLabel, ampmLabel;
		
		public ClockMessage(){
			this.setLayout(null);
			
			timeLabel = new JLabel(time);
			timeLabel.setBounds(28,0,100,30);
			timeLabel.setForeground(Color.BLACK);
			timeLabel.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,18));
			
			ampmLabel = new JLabel(amPm[i]);
			ampmLabel.setBounds(0,-13,40,60);
			ampmLabel.setForeground(Color.BLACK);
			ampmLabel.setFont(new Font("¸¼Àº °íµñ",Font.BOLD,14));
			
			add(timeLabel);
			add(ampmLabel);
		}
		
		public void run() {
			do{
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				timeLabel.setText(sdf.format(new Date()));
			}while(true);
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == logout){
			JOptionPane.showMessageDialog(null,"Logout!");
			try {
				new LB_login();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			setVisible(false);
		}else if( e.getSource() == ¶ó¼î¸ù){
			new bookInfo(0);
			
		}else if( e.getSource() == ¹é¹üÀÏÁö){
			new bookInfo(1);
			
		}else if( e.getSource() == ÀÌ±âÀûÀÎÀ¯ÀüÀÚ){
			new bookInfo(2);
			
		}else if( e.getSource() == ÀÚº»ÁÖÀÇ4_0){
			new bookInfo(3);
			
		}else if( e.getSource() == Á×ÀÌ´ÂÃ¥){
			new bookInfo(4);
			
		}else if( e.getSource() == ÃÑ±Õ¼è){
			
			new bookInfo(5);
		}else if( e.getSource() == search){
			setVisible(false); // ÇöÀç ÇÁ·¹ÀÓÀ» ¾Èº¸ÀÌ°ÔÇÔ
			dispose();
			try {
				new Search();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if( e.getSource() == status){
			setVisible(false); // ÇöÀç ÇÁ·¹ÀÓÀ» ¾Èº¸ÀÌ°ÔÇÔ
			dispose();
			try {
				new My_status();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if( e.getSource() == inquire){
			new Inquire();
		}
		
	}
	class Inquire extends JFrame implements ActionListener{
		JLayeredPane layeredPan;
		BufferedImage img = null;
		JButton back;
		JTextArea txt_inquire;
		
		Font TX_font = new Font("¸¼Àº °íµñ",Font.BOLD,23);
		final int user_num = LB_login.user_num;	
		
		Inq inQ[] = new Inq[100];
		int counter;
		String inq;
		
		Inquire(){
			try {
				new fileRead();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setTitle("Inquire");
			setSize(700,400);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension frameSize = this.getSize();
			Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((windowSize.width - frameSize.width)/2+(windowSize.width - frameSize.width)/4,(windowSize.height - frameSize.height)/2);
			
	
			setLayout(null);
			layeredPan = new JLayeredPane();
			layeredPan.setBounds(0,0,700,400);
			layeredPan.setLayout(null);
			
			try {
				img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ad¹è°æ.png"));
			} catch (Exception e) {
				System.out.println("Load error");
				System.exit(0);
			}
			
			MyPanel pn = new MyPanel();
			pn.setBounds(0,0,700,400);
			
			back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_back1.png"));
			back.setBounds(-20,0,250,80);
			back.addActionListener(this);
			
			back.setBorderPainted(false);
			back.setFocusPainted(false);
			back.setContentAreaFilled(false);
			

			
			txt_inquire = new JTextArea(30, 10);
			txt_inquire.setFont(TX_font);
			txt_inquire.setBounds(50,45,600,300);
			txt_inquire.setOpaque(false);
			
			
			layeredPan.add(txt_inquire);
			layeredPan.add(back);			
			layeredPan.add(pn);
			add(layeredPan);
			setUndecorated(true);
			setVisible(true);
		}

		class MyPanel extends JPanel{
			public void paint(Graphics g){
				g.drawImage(img, 0, 0, null);
			}
		}
		void main(){
			new Inquire();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == back){
				setVisible(false); // ÇöÀç ÇÁ·¹ÀÓÀ» ¾Èº¸ÀÌ°ÔÇÔ
				dispose();
			}else if(e.getSource() == inquire){
				
				inq = txt_inquire.getText();
				try {
					new fileWrite();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		}
		class fileRead{
	        @SuppressWarnings("resource")
			fileRead() throws IOException{
	        	String[] tmp = new String[2];
	            int i = 0;
	            StringTokenizer token;
		            try{
		                File aFile = new File("C:/Users/aolo26698824/workspace/JAVA/src/Inquire.txt");
		                FileReader fileReader = new FileReader(aFile);
		                BufferedReader reader = new BufferedReader(fileReader);
		            
		                String line = null;
		                
		                while((line = reader.readLine()) != null){
		                    token = new StringTokenizer(line,"%%");
		                    while(token.hasMoreTokens()){
		                    	tmp[i] = token.nextToken();
		                    	i++;
		                    }
		                    inQ[counter] = new Inq();
		                    inQ[counter].stuNum = tmp[0];
		                    inQ[counter].InQ = tmp [1];
		                   System.out.println(counter + "  " +inQ[counter].stuNum + "  " + inQ[counter].InQ);
		                    counter++;
		                    i = 0;
		                }
		                
		            }catch(Exception ex){
		    	           System.out.println(ex.toString()); //¿¡·¯Ã£±â
			        }
	        }
		}
		class fileWrite{
			fileWrite() throws IOException{
					try{
						BufferedWriter fw;
						fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/Inquire.txt"));
							if(counter == 1){
								fw.write(inQ[0].stuNum + "%%" + inQ[0].InQ + "%%");
								fw.write(stu_num + "%%" + inq + "%%");
							}else if(counter > 1){
								for(int i = 0; i < counter-1; i ++){
									fw.write(inQ[i].stuNum + "%%" + inQ[i].InQ + "%%");
								}
								fw.write(inQ[counter-1].stuNum + "%%" + inQ[counter-1].InQ + "%%");
							}else{
								fw.write(stu_num+"%%"+inq + "%%");
							}
						fw.close();
						System.out.println("WRITED");
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
			}
		class Inq{
			String stuNum;
			String InQ;
			
		}
	}
	class info{
		String ID;
		String PW;
		String Name;
		String stu_num;
		String aff;//ÇÐ°ú
		String ph_Num;
		int total_lend;
		int now_lend;
		int arrears;//¿¬Ã¼
		int chk_master;
		int lendBookNum[] = new int [5];
		String dateUntilBook[] = new String [5];
		int extendtionFlag[] = new int [5];
	}
}
