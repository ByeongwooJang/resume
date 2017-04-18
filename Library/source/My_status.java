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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Library.LB_login.MyPanel;



public class My_status extends JFrame implements ActionListener{
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JLabel stuNum, Name, HP, DP, PT_stuNum, PT_Name, PT_HP, PT_DP;
	JButton 대출현황, 뒤로가기,정보수정;
	bookData Book[] = new bookData[10000];
	static info info[] = new info[1000];
	
	int counter_ = 0;
	int counter = 0;
	final int user_num = LB_login.user_num;	
	
	
	Font LB_font = new Font("함초롬돋움",Font.BOLD,30);
	Font LB_font2 = new Font("함초롬돋움",Font.BOLD,26);
	
	My_status() throws IOException{
		new fileRead1();
		new fileRead();
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/배경2.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		Name = new JLabel("이름");
		Name.setBounds(30, 100, 200, 80);
		Name.setFont(LB_font);
		
		for(int i = 0; i < counter_; i ++)
		System.out.println("Status!!! " + info[i].Name+" " + info[i].stu_num);

		PT_Name = new JLabel(info[user_num].Name);
		PT_Name.setBounds(200,100, 200, 80);
		PT_Name.setFont(LB_font2);
		
		
		stuNum = new JLabel("학번");
		stuNum.setBounds(30, 160, 200, 80);
		stuNum.setFont(LB_font);
		
		PT_stuNum = new JLabel(info[user_num].stu_num);
		PT_stuNum.setBounds(200, 160, 200, 80);
		PT_stuNum.setFont(LB_font2);
		
		DP = new JLabel("소속");
		DP.setBounds(30, 220, 200, 80);
		DP.setFont(LB_font);
		
		PT_DP = new JLabel(info[user_num].aff);
		PT_DP.setBounds(200, 220, 200, 80);
		PT_DP.setFont(LB_font2);
		
		HP = new JLabel("H  P");
		HP.setBounds(30, 280, 200, 80);
		HP.setFont(LB_font);
		
		PT_HP = new JLabel(info[user_num].ph_Num);
		PT_HP.setBounds(200, 280, 200, 80);
		PT_HP.setFont(LB_font2);
		
		대출현황 = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_대출현황.png"));
		대출현황.setBounds(10,400,250,80);
		대출현황.addActionListener(this);
		
		대출현황.setBorderPainted(false);
		대출현황.setFocusPainted(false);
		대출현황.setContentAreaFilled(false);
		
		뒤로가기 = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_뒤로가기.png"));
		뒤로가기.setBounds(5,30,250,80);
		뒤로가기.addActionListener(this);
		
		뒤로가기.setBorderPainted(false);
		뒤로가기.setFocusPainted(false);
		뒤로가기.setContentAreaFilled(false);
		
		정보수정 = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_정보수정.png"));
		정보수정.setBounds(240,400,250,80);
		정보수정.addActionListener(this);
		
		정보수정.setBorderPainted(false);
		정보수정.setFocusPainted(false);
		정보수정.setContentAreaFilled(false);

		
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,680,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		
		layeredPan.add(clockMessage);
		layeredPan.add(정보수정);
		layeredPan.add(뒤로가기);
		layeredPan.add(대출현황);
		layeredPan.add(PT_HP);
		layeredPan.add(PT_DP);
		layeredPan.add(PT_stuNum);
		layeredPan.add(PT_Name);
		layeredPan.add(HP);
		layeredPan.add(DP);
		layeredPan.add(stuNum);
		layeredPan.add(Name);
		layeredPan.add(pn);
		add(layeredPan);
		setUndecorated(true);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == 뒤로가기){
			setVisible(false); // 현재 프레임을 안보이게함
			dispose();
			new User_window();
		}else if( e.getSource() == 정보수정){
			new Revise();
		}else if( e.getSource() == 대출현황){
			new Show();
		}
		
	}
	class MyPanel extends JPanel{
		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new My_status();
	}
	class Show extends JFrame implements ActionListener{
		JLayeredPane layeredPan;
		BufferedImage img = null;
		JButton back, extend;
		JTextField txt_bookNum;
		JTable table;
		DefaultTableModel model;
		JScrollPane js;
		
		Font TX_font = new Font("Bell MT",Font.BOLD,23);
		
		
		Show(){
			setTitle("Revise");
			setSize(1150,300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension frameSize = this.getSize();
			Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((windowSize.width - frameSize.width)/2,(windowSize.height - frameSize.height)/2);
			
	
			setLayout(null);
			layeredPan = new JLayeredPane();
			layeredPan.setBounds(0,0,1150,300);
			layeredPan.setLayout(null);
			
			try {
				img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/대출현황.png"));
			} catch (Exception e) {
				System.out.println("Load error");
				System.exit(0);
			}
			
			MyPanel pn = new MyPanel();
			pn.setBounds(0,0,1150,300);
			
			back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_back1.png"));
			back.setBounds(-20,0,250,80);
			back.addActionListener(this);
			
			back.setBorderPainted(false);
			back.setFocusPainted(false);
			back.setContentAreaFilled(false);
			
			extend = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_연장하기.png"));
			extend.setBounds(980,50,200,80);
			extend.addActionListener(this);
			
			extend.setBorderPainted(false);
			extend.setFocusPainted(false);
			extend.setContentAreaFilled(false);
			
			txt_bookNum = new JTextField(14);
			txt_bookNum.setFont(TX_font);
			txt_bookNum.setBounds(850,70,110,30);
			txt_bookNum.setOpaque(false);
						
			String[][] rowData = new String[counter][7];
			int t = 0;
			
			for(int i = 0; i < 5; i++){
				if(info[user_num].lendBookNum[i] != -1){
					for(int j = 0; j < 7; j++){
						switch(j){
						case 0:
							rowData[t][j] = Book[info[user_num].lendBookNum[i]].cate01;					
							break;
						case 1:
							rowData[t][j] = Book[info[user_num].lendBookNum[i]].cate02;
							break;
						case 2:
							rowData[t][j] = String.valueOf(Book[info[user_num].lendBookNum[i]].bookNum);
							break;
						case 3:
							rowData[t][j] = Book[info[user_num].lendBookNum[i]].bookName;
							break;
						case 4:
							rowData[t][j] = Book[info[user_num].lendBookNum[i]].writer;
							break;
						case 5:
							rowData[t][j] = Book[info[user_num].lendBookNum[i]].madeBy;
							break;
						case 6:
							rowData[t][j] = info[user_num].dateUntilBook[i];
							break;
						}
					}
					t++;
				}
				
			}
			
			String[] title = {"카테고리01",
					"카테고리02",
					"책 번호",
					"책 제목",
					"저자",
					"출판사",
					"대출기한",
					};

			model = new DefaultTableModel(rowData,title);
			table = new JTable(model);
			table.setFillsViewportHeight(true);
			//cate 01
			table.getColumnModel().getColumn(0).setMaxWidth(110);
			table.getColumnModel().getColumn(0).setMinWidth(110);
			table.getColumnModel().getColumn(0).setWidth(110);
			//cate 02
			table.getColumnModel().getColumn(1).setMaxWidth(110);
			table.getColumnModel().getColumn(1).setMinWidth(110);
			table.getColumnModel().getColumn(1).setWidth(110);
			//book Num
			table.getColumnModel().getColumn(2).setMaxWidth(120);
			table.getColumnModel().getColumn(2).setMinWidth(120);
			table.getColumnModel().getColumn(2).setWidth(120);
			//bookName
			table.getColumnModel().getColumn(3).setMaxWidth(350);
			table.getColumnModel().getColumn(3).setMinWidth(350);
			table.getColumnModel().getColumn(3).setWidth(350);
			
			table.getColumnModel().getColumn(4).setMaxWidth(150);
			table.getColumnModel().getColumn(4).setMinWidth(150);
			table.getColumnModel().getColumn(4).setWidth(150);
			
			table.getColumnModel().getColumn(5).setMaxWidth(150);
			table.getColumnModel().getColumn(5).setMinWidth(150);
			table.getColumnModel().getColumn(5).setWidth(150);
			
			table.getColumnModel().getColumn(6).setMaxWidth(100);
			table.getColumnModel().getColumn(6).setMinWidth(100);
			table.getColumnModel().getColumn(6).setWidth(100);
			
			//Table SOrt
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			DefaultTableCellRenderer dtcr2 = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
			dtcr2.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			
			tcm.getColumn(0).setCellRenderer(dtcr2);
			tcm.getColumn(1).setCellRenderer(dtcr2);
			tcm.getColumn(2).setCellRenderer(dtcr2);
			tcm.getColumn(3).setCellRenderer(dtcr2);
			tcm.getColumn(4).setCellRenderer(dtcr2);
			tcm.getColumn(5).setCellRenderer(dtcr2);
			tcm.getColumn(6).setCellRenderer(dtcr2);
			
			
			js = new JScrollPane(table);
			js.setVisible(true);
			js.setBounds(30, 120, 1100, 150);
			
			
			layeredPan.add(txt_bookNum);
			layeredPan.add(extend);
			layeredPan.add(back);			
			layeredPan.add(js);
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
			new Show();
		}
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == back){
				setVisible(false); // 현재 프레임을 안보이게함
				dispose();
			}else if( e.getSource() == extend){
				int flag = 0;
				Calendar cal = new GregorianCalendar(Locale.KOREA);
				SimpleDateFormat DD = new SimpleDateFormat("yy-MM-dd");
				
				int index = 0;
				for(int i = 0 ; i < counter; i++){
					if(Book[i].bookNum.equals(txt_bookNum.getText())){
						System.out.println("index : " + i);
						index= i;
						break;
					}
				}
				for(int i = 0; i < 5; i ++){
					System.out.println("info num : " + info[user_num].lendBookNum[i] + " index : "+(index) + "   " +" flag : " +info[user_num].extendtionFlag[i]);
					if(info[user_num].lendBookNum[i] == index && info[user_num].extendtionFlag[i] == -2){
						Date tmp = null;
						try {
							tmp = DD.parse(info[user_num].dateUntilBook[i]);
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						cal.setTime(tmp);

						cal.add(Calendar.DAY_OF_YEAR, 7);
						
						info[user_num].dateUntilBook[i] = DD.format(cal.getTime());
						System.out.println(info[user_num].dateUntilBook[i]);
						info[user_num].extendtionFlag[i] = -3;
						try {
							new fileWrite();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,"연장완료!");
						flag = 1;
						break;
					}
				}
				if(flag == 0){
					JOptionPane.showMessageDialog(null,"최대 연장 1회");
				}
				flag = 1;
			}
			
		}
		
	}
	
	class Revise extends JFrame implements ActionListener{
		JLayeredPane layeredPan;
		BufferedImage img = null;
		JButton back, register;
		JLabel ID, PW, STU_NUM, DP, HP, NAME;
		JLabel PT_STU_NUM, PT_DP, PT_NAME;
		
		JTextField txt_ID,txt_PW,txt_HP;
		
		final int user_num = LB_login.user_num;	
		
		Font LB_font = new Font("Bell MT",Font.BOLD,25);
		Font LB_font2 = new Font("맑은 고딕",Font.BOLD,25);
		Font TX_font = new Font("Bell MT",Font.BOLD,20);
			
		Revise(){
			setTitle("Revise");
			setSize(400,500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension frameSize = this.getSize();
			Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((windowSize.width - frameSize.width)/2+((windowSize.width - frameSize.width)/4),(windowSize.height - frameSize.height)/2);
			
	
			setLayout(null);
			layeredPan = new JLayeredPane();
			layeredPan.setBounds(0,0,400,500);
			layeredPan.setLayout(null);
			
			try {
				img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/정보수정.png"));
			} catch (Exception e) {
				System.out.println("Load error");
				System.exit(0);
			}
			
			MyPanel pn = new MyPanel();
			pn.setBounds(0,0,400,500);
			
			back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_back1.png"));
			back.setBounds(-20,0,250,80);
			back.addActionListener(this);
			
			back.setBorderPainted(false);
			back.setFocusPainted(false);
			back.setContentAreaFilled(false);
			
			ID = new JLabel("I D");
			ID.setBounds(40, 60, 200, 80);
			ID.setFont(LB_font);
			
			txt_ID = new JTextField(14);
			txt_ID.setFont(TX_font);
			txt_ID.setBounds(140,95,200,30);
			txt_ID.setOpaque(false);
			txt_ID.setText(info[user_num].ID);
			
			PW = new JLabel("P W");
			PW.setBounds(40, 110, 200, 80);
			PW.setFont(LB_font);
			
			txt_PW = new JTextField(14);
			txt_PW.setFont(TX_font);
			txt_PW.setBounds(140,145,200,30);
			txt_PW.setOpaque(false);
			txt_PW.setText(info[user_num].PW);
			
			STU_NUM = new JLabel("학번");
			STU_NUM.setBounds(40, 160, 200, 80);
			STU_NUM.setFont(LB_font2);
			
			PT_STU_NUM = new JLabel(info[user_num].stu_num);
			PT_STU_NUM.setBounds(140, 160, 200, 80);
			PT_STU_NUM.setFont(LB_font2);
			
			NAME = new JLabel("이름");
			NAME.setBounds(40, 210, 200, 80);
			NAME.setFont(LB_font2);
			
			PT_NAME = new JLabel(info[user_num].Name);
			PT_NAME.setBounds(140, 210, 200, 80);
			PT_NAME.setFont(LB_font2);
			
			DP = new JLabel("소속");
			DP.setBounds(40, 260, 200, 80);
			DP.setFont(LB_font2);
			
			PT_DP = new JLabel(info[user_num].aff);
			PT_DP.setBounds(140, 260, 200, 80);
			PT_DP.setFont(LB_font2);
			
			HP = new JLabel("H  P");
			HP.setBounds(40, 310, 200, 80);
			HP.setFont(LB_font2);
			
			txt_HP = new JTextField(14);
			txt_HP.setFont(TX_font);
			txt_HP.setBounds(140,345,200,30);
			txt_HP.setOpaque(false);
			txt_HP.setText(info[user_num].ph_Num);
			
			register = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_수정하기.png"));
			register.setBounds(230,430,200,70);
			register.addActionListener(this);
			
			register.setBorderPainted(false);
			register.setFocusPainted(false);
			register.setContentAreaFilled(false);

			
			layeredPan.add(register);
			layeredPan.add(txt_HP);
			layeredPan.add(txt_PW);
			layeredPan.add(txt_ID);
			layeredPan.add(PT_NAME);
			layeredPan.add(PT_DP);
			layeredPan.add(PT_STU_NUM);
			layeredPan.add(HP);
			layeredPan.add(DP);
			layeredPan.add(NAME);
			layeredPan.add(STU_NUM);
			layeredPan.add(PW);
			layeredPan.add(ID);
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
			new Revise();
		}
		@Override
		public void actionPerformed(ActionEvent ea) {
			if(ea.getSource() == back){
				setVisible(false); // 현재 프레임을 안보이게함
				dispose();
			}else if( ea.getSource() == register){
				info[user_num].ID = txt_ID.getText();
				info[user_num].PW = txt_PW.getText();
				info[user_num].ph_Num = txt_HP.getText();
				try {
					new fileWrite();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"수정완료!");
				txt_ID.setText(info[user_num].ID);
				txt_PW.setText(info[user_num].PW);
				txt_HP.setText(info[user_num].ph_Num);
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
	class fileRead{
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
	                    System.out.println(Book[counter].cate01 + "|"+Book[counter].cate02+"|"+Book[counter].bookNum+"|"+Book[counter].bookName+"|"+Book[counter].writer+"|"+Book[counter].madeBy+"|"+Book[counter].lend);
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
	
	class bookData{
		String cate01;
		String cate02;
		String bookNum;
		String bookName;
		String writer;
		String madeBy;
		int lend;
	}
	
	class info{//책 연장 확인하는 5개 배열 만들고 텍스트 파일 쓰기
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
	
}
