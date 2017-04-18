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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JTextField;

import Library.Search.bookData;
import Library.Search.info;
import Library.Search.total;
import Library.User_window.ClockMessage;
import Library.User_window.MyPanel;

public class AD_window extends JFrame implements ActionListener{
	
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JButton 반납, 통계, 통계2, 회원관리, 책추가,  AD, logout;
	JLabel book;
	JTextField txt_bookNum;
	
	
	info info[] = new info[10000];
	bookData Book[] = new bookData[10000];
	total total[] = new total[10000];
	
	static int lend_flag_flag = 0;
	static int enter_count = 0;
	static int aff_count[] = new int[11];
	static int cate_count[] = new int[10];
	String cate[] = {"총류","철학","종교","사회과학","순수과학","기술과학","예술","언어","문학","역사"};
	String DP[] = {"컴퓨터공학과","멀티미디어공학과","정보기술공학과","전기공학과","정보통신공학과","기계자동차공학부","산업경영공학과","간호학과","유아교육과","다문화학과","복지학과"};
	final int lend_flag = Search.lend_flag;
	
	int counter = 0;
	int counter_ = 0;
	int counter__ = 0;
	
	String stu_num = LB_login.stu_number;
	Font LB_font = new Font("HY궁서B",Font.BOLD,32);
	Font TX_font = new Font("Bell MT",Font.BOLD,20);
	AD_window() throws IOException {
		enter_count ++;
		new fileRead();
		new fileRead1();
		new fileRead1_();
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Admin_back.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		txt_bookNum = new JTextField(30);
		txt_bookNum.setFont(TX_font);
		txt_bookNum.setBounds(140,120,140,30);
		txt_bookNum.setOpaque(false);
		txt_bookNum.setForeground(Color.white);
		
		반납 = new JButton();
		반납.setBounds(290,120,80,50);
		반납.addActionListener(this);
		
		반납.setBorderPainted(false);
		반납.setFocusPainted(false);
		반납.setContentAreaFilled(false);
		
		통계 = new JButton();
		통계.setBounds(130,220,190,50);
		통계.addActionListener(this);
		
		통계.setBorderPainted(false);
		통계.setFocusPainted(false);
		통계.setContentAreaFilled(false);
	
		통계2 = new JButton();
		통계2.setBounds(130,320,260,50);
		통계2.addActionListener(this);
		
		통계2.setBorderPainted(false);
		통계2.setFocusPainted(false);
		통계2.setContentAreaFilled(false);
		
		회원관리 = new JButton();
		회원관리.setBounds(130,430,200,50);
		회원관리.addActionListener(this);
		
		
		회원관리.setBorderPainted(false);
		회원관리.setFocusPainted(false);
		회원관리.setContentAreaFilled(false);
		
		책추가 = new JButton();
		책추가.setBounds(130,530,200,50);
		책추가.addActionListener(this);
		
		책추가.setBorderPainted(false);
		책추가.setFocusPainted(false);
		책추가.setContentAreaFilled(false);
		
		AD = new JButton();
		AD.setBounds(130,630,200,50);
		AD.addActionListener(this);
		
		AD.setBorderPainted(false);
		AD.setFocusPainted(false);
		AD.setContentAreaFilled(false);
		
		
		logout = new JButton();
		logout.setBounds(1130,30,100,50);
		logout.addActionListener(this);
		
		logout.setBorderPainted(false);
		logout.setFocusPainted(false);
		logout.setContentAreaFilled(false);
		
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,660,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		for(int k = 0; k < 11 ; k++)
			System.out.print(aff_count[k] +" ");
		
		for(int k = 0; k < 10 ; k++)
			System.out.print("  " +cate_count[k]);
		
		
		layeredPan.add(txt_bookNum);
		layeredPan.add(clockMessage);
		layeredPan.add(AD);
		layeredPan.add(책추가);
		layeredPan.add(회원관리);
		layeredPan.add(통계2);
		layeredPan.add(통계);
		layeredPan.add(반납);
		layeredPan.add(logout);
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
	
	
	class AD_modify extends JFrame implements ActionListener{
		JLayeredPane layeredPan;
		BufferedImage img = null;
		JButton back, register;
				
		JTextField txt_ad1, txt_ad2, txt_ad3,txt_ad4;
		
		JLabel ad1, ad2, ad3, ad4;
		
		String AD_location[] = new String[4];
		final int user_num = LB_login.user_num;	
		
		Font LB_font = new Font("Bell MT",Font.BOLD,25);
		Font LB_font2 = new Font("맑은 고딕",Font.BOLD,12);
		Font TX_font = new Font("Bell MT",Font.BOLD,20);
		AD_modify(){
			try {
				new fileRead1__AD();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			setTitle("Revise");
			setSize(600,500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension frameSize = this.getSize();
			Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((windowSize.width - frameSize.width)/2+((windowSize.width - frameSize.width)/4),(windowSize.height - frameSize.height)/2);
			
	
			setLayout(null);
			layeredPan = new JLayeredPane();
			layeredPan.setBounds(0,0,600,500);
			layeredPan.setLayout(null);

			try {
				img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/AD수정배경.png"));
			} catch (Exception e) {
				System.out.println("Load error");
				System.exit(0);
			}
			
			MyPanel pn = new MyPanel();
			pn.setBounds(0,0,600,500);
			
			back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_close.png"));
			back.setBounds(0,0,250,80);
			back.addActionListener(this);
			
			back.setBorderPainted(false);
			back.setFocusPainted(false);
			back.setContentAreaFilled(false);
			
			ad1 = new JLabel("AD_1");
			ad1.setBounds(40, 60, 200, 80);
			ad1.setFont(LB_font);
			
			txt_ad1 = new JTextField(50);
			txt_ad1.setFont(LB_font2);
			txt_ad1.setBounds(140,95,400,30);
			txt_ad1.setOpaque(false);
			txt_ad1.setText(AD_location[0]);

			ad2 = new JLabel("AD_2");
			ad2.setBounds(40, 130, 200, 80);
			ad2.setFont(LB_font);
			
			txt_ad2 = new JTextField(50);
			txt_ad2.setFont(LB_font2);
			txt_ad2.setBounds(140,165,400,30);
			txt_ad2.setOpaque(false);
			txt_ad2.setText(AD_location[1]);
			
			ad3 = new JLabel("AD_3");
			ad3.setBounds(40, 200, 200, 80);
			ad3.setFont(LB_font);
			
			txt_ad3 = new JTextField(50);
			txt_ad3.setBounds(140, 235, 400, 30);
			txt_ad3.setFont(LB_font2);
			txt_ad3.setOpaque(false);
			txt_ad3.setText(AD_location[2]);
			
			ad4 = new JLabel("AD_4");
			ad4.setBounds(40, 270, 245, 80);
			ad4.setFont(LB_font);
			
			txt_ad4 = new JTextField(50);
			txt_ad4.setBounds(140, 305, 400, 30);
			txt_ad4.setFont(LB_font2);
			txt_ad4.setOpaque(false);
			txt_ad4.setText(AD_location[3]);
			
			
			register = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_등록하기.png"));
			register.setBounds(400,400,250,80);
			register.addActionListener(this);
			
			register.setBorderPainted(false);
			register.setFocusPainted(false);
			register.setContentAreaFilled(false);
			
			
			layeredPan.add(register);
			layeredPan.add(txt_ad1);
			layeredPan.add(txt_ad2);
			layeredPan.add(txt_ad3);
			layeredPan.add(txt_ad4);
			layeredPan.add(ad1);
			layeredPan.add(ad2);
			layeredPan.add(ad3);
			layeredPan.add(ad4);
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
		public void main() {
			// TODO Auto-generated method stub
			new AD_modify();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == back){
				setVisible(false); // 현재 프레임을 안보이게함
				dispose();
			}else if(e.getSource() == register){
				AD_location[0] = txt_ad1.getText();
				AD_location[1] = txt_ad2.getText();
				AD_location[2] = txt_ad3.getText();
				AD_location[3] = txt_ad4.getText();
				try {
					new fileWrite_AD();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		class fileRead1__AD{
	        fileRead1__AD() throws IOException{
	            int i = 0;
	            StringTokenizer token;
		            try{
		                File aFile = new File("C:/Users/aolo26698824/workspace/JAVA/src/AD.txt");
		                FileReader fileReader = new FileReader(aFile);
		                BufferedReader reader = new BufferedReader(fileReader);
		            
		                String line = null;
		                
		                while((line = reader.readLine()) != null){
		                    token = new StringTokenizer(line," \n");
		                    System.out.println("!!!!\n" + line);
		                    while(token.hasMoreTokens()){
		                    	AD_location[i] = token.nextToken();  
		                    	i++;
		                    }              
		                }
		                
		            }catch(Exception ex){
		    	           System.out.println("ERROR!!bbb"); 
			        }
		            i = 0;
	        }
		}
		class fileWrite_AD{
			fileWrite_AD() throws IOException{
					try{
						BufferedWriter fw;
						fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/AD.txt"));
							for(int i = 0; i < 4; i ++){
								fw.write(AD_location[i]+"\n");
							}
						fw.close();
						System.out.println("WRITED");
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
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
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new AD_window();
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		if(a.getSource() == 통계){
			try {
				new AD_Graphic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if( a.getSource() == 통계2){
			try {
				new AD_Graphic2();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if( a.getSource() == 반납){
			int index = -1;
			for(int i = 0 ; i < counter; i++){
				if(Book[i].bookNum.equals(txt_bookNum.getText())){
					index = i;
					break;
				}
			}
			if(index != -1){
				for(int i = 0; i < counter_; i++){
					for(int j = 0; j <5; j++){
						if(info[i].lendBookNum[j] == index){
							info[i].lendBookNum[j] = -1;
							info[i].dateUntilBook[j] = "0";
							info[i].extendtionFlag[i] = -2;
							Book[index].lend = 0;
							break;
						}
					}
				}
				try {
					new fileWrite();
					new fileWrite_book();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"반납완료!");
			}else{
				JOptionPane.showMessageDialog(null,"책 번호 오류!");
			}	
		}else if(a.getSource() == 책추가){
			setVisible(false); // 현재 프레임을 안보이게함
			dispose();
			try {
				new AD_Book_InsertDel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if( a.getSource() == AD){
			new AD_modify();
		}else if( a.getSource() == logout){
			JOptionPane.showMessageDialog(null,"Logout!");
			try {
				new LB_login();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			setVisible(false);
		}else if( a.getSource() == 회원관리){
			new AD_user();
			dispose();
			setVisible(false);
		}
	}class fileRead{
        @SuppressWarnings("resource")
		fileRead() throws IOException{
        	String[] tmp = new String[7];
            int i = 0;
            StringTokenizer token;
	            try{
	                File aFile = new File("C:/Users/aolo26698824/workspace/JAVA/src/book_data.txt");
	                FileReader fileReader = new FileReader(aFile);
	                BufferedReader reader = new BufferedReader(fileReader);
	            
	                String line = null;
	                
	                while((line = reader.readLine()) != null){
	                    token = new StringTokenizer(line,"%%\n");
	                    while(token.hasMoreTokens()){
	                    	tmp[i] = token.nextToken();  
	                    	i++;
	                    }
	                    Book[counter] = new bookData();
	                    Book[counter].cate01 = tmp[0];
	                    Book[counter].cate02 = tmp[1];
	                    Book[counter].bookNum = tmp[2];
	                    Book[counter].bookName = tmp[3];
	                    Book[counter].writer = tmp[4];
	                    Book[counter].madeBy = tmp[5];
	                    Book[counter].lend = Integer.parseInt(tmp[6]);
	                    System.out.println(Book[counter].cate01 + "||||"+Book[counter].cate02+"||||"+Book[counter].bookNum+"||||"+Book[counter].bookName+"||||"+Book[counter].writer+"||||"+Book[counter].madeBy+"||||"+Book[counter].lend);
	                    counter++;
	                    i = 0;
	                }
	                
	            }catch(Exception ex){
	    	           System.out.println(ex.toString()); //에러찾기
		        }
        }
	}
	class fileRead1{
        fileRead1() throws IOException{
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
	                    info[counter_] = new info();
	                    info[counter_].ID = tmp[0];
	                    info[counter_].PW = tmp[1];
	                    info[counter_].Name = tmp [2];
	                    info[counter_].stu_num = tmp[3];
	                    info[counter_].aff = tmp[4];
	                    info[counter_].ph_Num = tmp[5];
	                    info[counter_].total_lend = Integer.parseInt(tmp[6]);
	                    info[counter_].now_lend = Integer.parseInt(tmp[7]);
	                    info[counter_].arrears = Integer.parseInt(tmp[8]);
	                    info[counter_].chk_master = Integer.parseInt(tmp[9]);
	                    for(int z = 0; z < 5; z++)
	                    info[counter_].lendBookNum[z] = Integer.parseInt(tmp[10+z]+"");
	                    for(int z = 0; z < 5; z++)
		                    info[counter_].dateUntilBook[z] = tmp[15+z];
	                    for(int z = 0; z < 5; z++)
		                    info[counter_].extendtionFlag[z] = Integer.parseInt(tmp[20+z]);
	                    counter_++;
	                    i = 0;
	                    
	                }
	                
	            }catch(Exception ex){
	    	           System.out.println("ERROR!!bbb"); 
		        }
        }
	}
	class fileRead1_{
        fileRead1_() throws IOException{
        	if(enter_count == 1 || lend_flag == 1){
        	lend_flag_flag = 1;
            String[] tmp = new String[3];
            int i = 0;
            StringTokenizer token;
	            try{
	                File aFile = new File("C:/Users/aolo26698824/workspace/JAVA/src/total.txt");
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
	                    total[counter__] = new total();
	                    total[counter__].stu_num = tmp[0];
	                    total[counter__].aff = tmp[1];
	                    total[counter__].cate01 = tmp[2];
	                   	            
	                    for(int ii = 0; ii < 10; ii++){
                    		if(cate[ii].equals(total[counter__].cate01)){
                    			cate_count[ii] ++;
                    			break;
                    		}
	                    }
	                    
	                    for(int ii = 0; ii < 11; ii++){
	                    	if(DP[ii].equals(total[counter__].aff)){
	                    			aff_count[ii] ++;
	                    			break;
	                    	}
	                    	
	                    }
	                    counter__++;
	                    i = 0;
	                }
	                
	            }catch(Exception ex){
	    	           System.out.println("ERROR!!bbb"); 
		        }
        	}
        }
	}	
	class fileWrite{
		fileWrite() throws IOException{
			try{
					BufferedWriter fw;
					fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/LB_User_Data.txt"));
						for(int i = 0; i < counter_; i ++){
							fw.write(info[i].ID+" "+ info[i].PW+" "+ info[i].Name+" "+ info[i].stu_num + " " + info[i].aff +" "+ info[i].ph_Num + " "+ info[i].total_lend +" "+info[i].now_lend + " " + info[i].arrears+ " " +info[i].chk_master+ " " +info[i].lendBookNum[0] + " " +info[i].lendBookNum[1] +  " " +info[i].lendBookNum[2] +  " " +info[i].lendBookNum[3] +  " " +info[i].lendBookNum[4] +  " " +info[i].dateUntilBook[0] +" "+info[i].dateUntilBook[1] +" "+info[i].dateUntilBook[2] +" "+info[i].dateUntilBook[3] +" "+info[i].dateUntilBook[4] +" "+info[i].extendtionFlag[0] + " "+info[i].extendtionFlag[1] + " "+info[i].extendtionFlag[2] + " "+info[i].extendtionFlag[3] + " "+info[i].extendtionFlag[4] +"\n");
						}
					fw.close();
					System.out.println("WRITED");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
	
	
	class fileWrite_book{
		fileWrite_book() throws IOException{
				try{
					BufferedWriter fw;
					fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/book_data.txt"));
						for(int i = 0; i < counter; i ++){
							fw.write(Book[i].cate01 + "%%"+Book[i].cate02+"%%"+Book[i].bookNum+"%%"+Book[i].bookName+"%%"+Book[i].writer+"%%"+Book[i].madeBy+"%%"+Book[i].lend+"\n");
						}
					fw.close();
					System.out.println("WRITED");
				}catch(Exception ex){
					ex.printStackTrace();
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
		String[] dateUntilBook = new String [5];
		int extendtionFlag[] = new int [5];
	}
	class bookData{
		String cate01;
		String cate02;
		String bookNum;
		String bookName;
		String writer;
		String madeBy;
		int lend;
	}
	class total{
		String stu_num;
		String aff;
		String cate01;
	}

}
