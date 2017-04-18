package Library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class LB_login extends JFrame implements ActionListener{
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JButton login, register;
	
	static info info[] = new info[1000];
	static info user_info;
	
	JTextField txt_ID, txt_PW;
	static int counter = 0;
	static int user_num = -1;
	static String stu_number;
	
	Font LB_font = new Font("Bell MT",Font.BOLD,25);
	Font TX_font = new Font("Bell MT",Font.BOLD,20);
	Font TX_font2 = new Font("맑은 고딕",Font.BOLD,20);
	public LB_login() throws IOException{
		new fileRead();
		user_num = -1;
		setTitle("Login");
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/로그인 배경.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		txt_ID = new JTextField(30);
		txt_ID.setFont(TX_font);
		txt_ID.setBounds(580,385,160,30);
		txt_ID.setOpaque(false);
		txt_ID.setBorder(null);
		
		txt_PW = new JPasswordField(30);
		txt_PW.setFont(TX_font);
		txt_PW.setBounds(580,425,160,30);
		txt_PW.setOpaque(false);
		txt_PW.setBorder(null);
		
		login = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_login.png"));
		login.setBounds(695,470,80,50);
		login.addActionListener(this);
		
		login.setBorderPainted(false);
		login.setFocusPainted(false);
		login.setContentAreaFilled(false);
		
		register = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_register.png"));
		register.setBounds(610,470,80,50);
		register.addActionListener(this);
		
		register.setBorderPainted(false);
		register.setFocusPainted(false);
		register.setContentAreaFilled(false);
		
		
		
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,660,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		
		
		layeredPan.add(clockMessage);
		layeredPan.add(txt_PW);
		layeredPan.add(txt_ID);
		layeredPan.add(register);
		layeredPan.add(login);
		layeredPan.add(pn);
		add(layeredPan);
		setVisible(true);
	
	}
	class MyPanel extends JPanel{
		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		new LB_login();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login){
			int chk = 0;
			String id, pw;
			id = txt_ID.getText();
			pw = txt_PW.getText();
			System.out.println(id+ pw);
			for(int i = 0; i < counter ; i++){
				if(id.equals(info[i].ID) && pw.equals(info[i].PW)){
					user_num = i; // 유저 정보를 유저 윈도우 클레스로 넘겨줄겅미
					System.out.println(" 카운터 : " + counter +"USER_NUMBER: "+ user_num);
					stu_number = info[user_num].stu_num;
					user_info = info[user_num];
					System.out.println("OK");
					if(info[i].chk_master == 1){
						chk = 2;
					}else{
						chk = 1;
					}
				}
			}
			if( chk == 1){
				JOptionPane.showMessageDialog(null,"Login Success!");
				new User_window();
				dispose();
				setVisible(false);
			}else if( chk == 2){
				JOptionPane.showMessageDialog(null,"Master Login Success!");
				try {
					new AD_window();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(null,"You Must Register!");
			}
			chk = 0;
		}else if( e.getSource() == register){
			setVisible(false); // 현재 프레임을 안보이게함
			dispose();
			new REGISTER();
		}
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
			timeLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
			
			ampmLabel = new JLabel(amPm[i]);
			ampmLabel.setBounds(0,-13,40,60);
			ampmLabel.setForeground(Color.BLACK);
			ampmLabel.setFont(new Font("맑은 고딕",Font.BOLD,14));
			
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
	
	class fileRead{
        fileRead() throws IOException{
            String[] tmp = new String[25];
            int i = 0;
            StringTokenizer token;
	            try{
	                File aFile = new File("C:/Users/aolo26698824/workspace/JAVA/src/LB_User_Data.txt");
	                FileReader fileReader = new FileReader(aFile);
	                BufferedReader reader = new BufferedReader(fileReader);
	            
	                String line = null;
	                
	                while((line = reader.readLine()) != null){
	                    token = new StringTokenizer(line," \n");
	                    System.out.println("!!!!\n" + line);
	                    while(token.hasMoreTokens()){
	                    	tmp[i] = token.nextToken();  
	                    	i++;
	                    }
	                    System.out.println("READY");
	                    info[counter] = new info();
	                    info[counter].ID = tmp[0];
	                    info[counter].PW = tmp[1];
	                    info[counter].Name = tmp [2];
	                    info[counter].stu_num = tmp[3];
	                    info[counter].aff = tmp[4];
	                    info[counter].ph_Num = tmp[5];
	                    info[counter].total_lend = Integer.parseInt(tmp[6]);
	                    info[counter].now_lend = Integer.parseInt(tmp[7]);
	                    info[counter].arrears = Integer.parseInt(tmp[8]);
	                    info[counter].chk_master = Integer.parseInt(tmp[9]);
	                    for(int z = 0; z < 5; z++)
	                    info[counter].lendBookNum[z] = Integer.parseInt(tmp[10+z]);
	                    for(int z = 0; z < 5; z++)
		                    info[counter].dateUntilBook[z] = tmp[15+z];
	                    for(int z = 0; z < 5; z++)
		                    info[counter].extendtionFlag[z] = Integer.parseInt(tmp[20+z]);
	                    counter++;
	                    i = 0;
	                }
	                
	            }catch(Exception ex){
	    	           System.out.println("ERROR!!xccc"); 
		        }
	            
        }
	}
}
class info{
	String ID;
	String PW;
	String Name;
	String stu_num;
	String aff;//학과
	String ph_Num;
	int total_lend;
	int now_lend;
	int arrears;//연체
	int chk_master;
	int lendBookNum[] = new int [5];
	String dateUntilBook[] = new String [5];
	int extendtionFlag[] = new int [5];
}