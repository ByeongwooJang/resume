package Library;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


class REGISTER extends JFrame implements ActionListener{
	BufferedImage img = null;
	JLayeredPane layeredPan01;
	BufferedImage img01 = null;
	JButton back,register;
	JLabel ID, PW, Name, stu_num, dp, phNum;
	JPasswordField txt_PW;
	JTextField txt_ID, txt_Name, txt_stu_num, txt_aff, txt_phNum;
	JComboBox<String> cmbBox;
	
	final info[] info = LB_login.info;

	int counter = LB_login.counter;
		
	String str_id, str_pw, str_aff,str_name,str_stu_num,str_ph_Num;
	String cate;
	String DP[] = {"choose","컴퓨터공학과","멀티미디어공학과","정보기술공학과","전기공학과","정보통신공학과","기계자동차공학부","산업경영공학과","간호학과","유아교육과","다문화학과","복지학과"};
	Font LB_font = new Font("Bell MT",Font.BOLD,25);
	Font TX_font = new Font("Bell MT",Font.BOLD,20);
	Font TX_font2 = new Font("맑은 고딕",Font.BOLD,20);
	
	public REGISTER() {
		
		System.out.println(counter+"!!");
		
		setTitle("Login");
		setSize(400,450);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width)/2,(windowSize.height - frameSize.height)/2);
		
		setLayout(null);
		layeredPan01 = new JLayeredPane();
		layeredPan01.setBounds(0,0,400,500);
		layeredPan01.setLayout(null);
		
		try {
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/회원가입.jpg"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,400,600);
		
		ID = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/id.png"));
		ID.setFont(LB_font);
		ID.setBounds(-10,60,100,40);

		txt_ID = new JTextField(30);
		txt_ID.setFont(TX_font);
		txt_ID.setBounds(150,65,200,30);
		txt_ID.setOpaque(false);
		
		
		
		PW = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/pw.png"));
		PW.setFont(LB_font);
		PW.setBounds(0,110,100,40);
		
		txt_PW = new JPasswordField(30);
		txt_PW.setFont(TX_font);
		txt_PW.setBounds(150,115,200,30);
		txt_PW.setOpaque(false);
		
		
		Name = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/name.png"));
		Name.setFont(LB_font);
		Name.setBounds(10,160,100,40);
		
		txt_Name = new JTextField(10);
		txt_Name.setFont(TX_font2);
		txt_Name.setBounds(150,165,200,30);
		txt_Name.setOpaque(false);
		
		
		stu_num = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/stuNum.png"));
		stu_num.setFont(LB_font);
		stu_num.setBounds(-25,210,200,40);
		
		txt_stu_num = new JTextField(10);
		txt_stu_num.setFont(TX_font);
		txt_stu_num.setBounds(150,215,200,30);
		txt_stu_num.setOpaque(false);
		
		
		dp = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/dp.png"));
		dp.setFont(LB_font);
		dp.setBounds(25,260,140,40);

		cmbBox = new JComboBox<String>(DP);
		cmbBox.setFont(TX_font2);
		cmbBox.setBounds(170,265,180,30);
		
		cmbBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cate = (String)cmbBox.getSelectedItem(); 
			}
		});
		
		phNum = new JLabel(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/HP.png"));
		phNum.setFont(LB_font);
		phNum.setBounds(-45,310,200,40);
		
		txt_phNum = new JTextField(14);
		txt_phNum.setFont(TX_font);
		txt_phNum.setBounds(150,315,200,30);
		txt_phNum.setOpaque(false);
		
		back = new JButton( new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_back.png"));
		back.setFont(LB_font);
		back.setBounds(0,-20,80,80);
		back.addActionListener(this);
		
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		
		register = new JButton( new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_regi_register.png"));
		register.setFont(LB_font);
		register.setBounds(233,360,200,50);
		register.addActionListener(this);
		
		register.setBorderPainted(false);
		register.setFocusPainted(false);
		register.setContentAreaFilled(false);
		

		layeredPan01.add(register);
		layeredPan01.add(cmbBox);
		layeredPan01.add(txt_phNum);
		layeredPan01.add(txt_stu_num);
		layeredPan01.add(txt_Name);
		layeredPan01.add(txt_PW);
		layeredPan01.add(txt_ID);
		layeredPan01.add(back);
		layeredPan01.add(phNum);
		layeredPan01.add(dp);
		layeredPan01.add(stu_num);
		layeredPan01.add(Name);
		layeredPan01.add(PW);
		layeredPan01.add(ID);
		layeredPan01.add(pn);
		
		setUndecorated(true);
		add(layeredPan01);
		setVisible(true);
	}
	class MyPanel extends JPanel{
		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back){
			setVisible(false); // 현재 프레임을 안보이게함
			dispose();
			try {
				new LB_login();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource() == register){
			str_id = txt_ID.getText();
			str_pw = txt_PW.getText();
			str_name= txt_Name.getText();
			str_stu_num = txt_stu_num.getText();
			str_aff = cate;
			str_ph_Num = txt_phNum.getText();
			
			cmbBox.setSelectedIndex(0);
			txt_ID.setText("");
			txt_PW.setText("");
			txt_Name.setText("");
			txt_stu_num.setText("");
			txt_phNum.setText("");
			try {
				new fileWrite();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"Registered!");
		}
	}
	class fileWrite{
		fileWrite() throws IOException{
				try{
					BufferedWriter fw;
					fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/LB_User_Data.txt"));
						if(counter != 0){
							fw.write(info[0].ID+" "+ info[0].PW+" "+ info[0].Name+" "+ info[0].stu_num + " " + info[0].aff +" "+ info[0].ph_Num + " "+ info[0].total_lend +" "+info[0].now_lend + " " + info[0].arrears+ " " +info[0].chk_master+ " " +info[0].lendBookNum[0]+ " " +info[0].lendBookNum[1]+  " " +info[0].lendBookNum[2]+  " " +info[0].lendBookNum[3]+  " " +info[0].lendBookNum[4] + " " + info[0].dateUntilBook[0]+ " " +info[0].dateUntilBook[1]+  " " +info[0].dateUntilBook[2]+  " " +info[0].dateUntilBook[3]+  " " +info[0].dateUntilBook[4] +" "+ info[0].extendtionFlag[0]+ " " +info[0].extendtionFlag[1]+  " " +info[0].extendtionFlag[2]+  " " +info[0].extendtionFlag[3]+  " " +info[0].extendtionFlag[4]+ "\n");
							for(int i = 1; i <= counter-1; i ++){
								fw.write(info[i].ID+" "+ info[i].PW+" "+ info[i].Name+" "+ info[i].stu_num + " " + info[i].aff +" "+ info[i].ph_Num + " "+ info[i].total_lend +" "+info[i].now_lend + " " + info[i].arrears+ " " +info[i].chk_master+ " " +info[i].lendBookNum[0]+ " " +info[i].lendBookNum[1]+  " " +info[i].lendBookNum[2]+  " " +info[i].lendBookNum[3]+  " " +info[i].lendBookNum[4] + " " + info[i].dateUntilBook[0]+ " " +info[i].dateUntilBook[1]+  " " +info[i].dateUntilBook[2]+  " " +info[i].dateUntilBook[3]+  " " +info[i].dateUntilBook[4] +" "+ info[i].extendtionFlag[0]+ " " +info[i].extendtionFlag[1]+  " " +info[i].extendtionFlag[2]+  " " +info[i].extendtionFlag[3]+  " " +info[i].extendtionFlag[4]+ "\n");
							}
							fw.write(str_id+" "+ str_pw+" "+ str_name+" "+ str_stu_num + " " + str_aff +" "+ str_ph_Num + " "+ 0 +" "+ 0 + " " + 0+ " " + 0+ " " +"-1"+ " " +"-1"+  " " +"-1"+  " " +"-1"+  " " +"-1" + " " + "0"+ " " +"0"+  " " +"0"+  " " +"0"+  " " +"0" + " " + "-2"+ " " +"-2"+  " " +"-2"+  " " +"-2"+  " " +"-2"+ "\n");
						}else{
							fw.write(str_id+" "+ str_pw+" "+ str_name+" "+ str_stu_num + " " + str_aff +" "+ str_ph_Num + " "+ 0 +" "+ 0 + " " + 0+ " " + 0+ " " +"-1"+ " " +"-1"+  " " +"-1"+  " " +"-1"+  " " +"-1"+ " " + "0"+ " " +"0"+  " " +"0"+  " " +"0"+  " " +"0"+ " " + "-2"+ " " +"-2"+  " " +"-2"+  " " +"-2"+  " " +"-2"+ "\n");
						}
					fw.close();
					System.out.println("WRITED");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}
}
