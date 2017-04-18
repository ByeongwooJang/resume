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
import javax.swing.table.DefaultTableModel;

import Library.AD_window.MyPanel;
import Library.AD_window.fileRead;
import Library.AD_window.fileRead1;
import Library.AD_window.fileRead1_;

public class AD_user extends JFrame implements ActionListener{
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JTable table;
	DefaultTableModel model;
	JScrollPane js;
	info info[] = new info[10000];
	JTextField stu_num;
	JLabel stu_num_;
	JButton reset, del, back;
	
	int counter = 0;
	Font LB_font = new Font("HY궁서B",Font.BOLD,32);
	Font TX_font = new Font("Bell MT",Font.BOLD,20);
	AD_user(){
		try {
			new fileRead();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Admin_user_back.jpg"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_나가기_2.png"));
		back.setBounds(40,40,120,50);
		back.addActionListener(this);
		
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		
		stu_num_ = new JLabel("학 번");
		stu_num_.setBounds(80,100,100,30);
		stu_num_.setForeground(Color.white);
		stu_num_.setFont(new Font("문체부 쓰기 정체",Font.BOLD,24));
		
		stu_num  = new JTextField(14);
		stu_num.setFont(TX_font);
		stu_num.setBounds(150, 100,150,30);
		stu_num.setOpaque(false);
		stu_num.setForeground(Color.white);
		
		
		reset = new JButton();
		reset.setBounds(80,210,200,40);
		reset.addActionListener(this);
		
		reset.setBorderPainted(false);
		reset.setFocusPainted(false);
		reset.setContentAreaFilled(false);
		
		del = new JButton();
		del.setBounds(80,310,200,40);
		del.addActionListener(this);
		
		del.setBorderPainted(false);
		del.setFocusPainted(false);
		del.setContentAreaFilled(false);
		
		
		
		
		String[][] rowData = new String[counter][24];
		
		for(int a = 0; a < counter; a++){
			for(int j = 0; j < 12; j++){
				switch(j){
				case 0:
					rowData[a][j] = String.valueOf(a+1);
					break;
				case 1:
					rowData[a][j] = (info[a].ID);
					break;
				case 2:
					rowData[a][j] = info[a].PW;
					break;
				case 3:
					rowData[a][j] = info[a].Name;
					break;
				case 4:
					rowData[a][j] = info[a].stu_num;
					break;
				case 5:
					rowData[a][j] = info[a].aff;
					break;
				case 6:
					rowData[a][j] = info[a].ph_Num;
					break;
				case 7:
					rowData[a][j] = String.valueOf(info[a].now_lend);
					break;
				case 8:
					rowData[a][j] = String.valueOf(info[a].arrears);
					break;
				case 9:
					for(int z = j; z < 14; z++){
						if(info[a].lendBookNum[z-j] >= 0)
							rowData[a][z] = String.valueOf(info[a].lendBookNum[z-j]);
						else
							rowData[a][z] = "-";
					}break;
				case 10:
					for(int z = 14, f = 0; z < 19; z++, f++){
						if(info[a].dateUntilBook[z-j-4].length() >= 2)
							rowData[a][z] = info[a].dateUntilBook[z-j-4].substring(3);
						else
							rowData[a][z] = "-";
					}
					break;
				case 11:
					for(int z = 19; z < 24; z++)
					if(info[a].extendtionFlag[z-j-8] == -2){
						rowData[a][z] = "O";
					}else{
						rowData[a][z] = "X";
					}
					break;	
			}
			System.out.println(rowData[a][1]);
		}
		}
		//테이블!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				String[] title = {"Index",
								"ID",
								"PW",
								"Name",
								"Stu_num",
								"Major",
								"Ph_num",
								"T_Lend",
								"Arr",
								"[Le","nd_","Bo","ok","num]",
								"[By"," "," "," ", " ]",
								"[Ex","te","nd","_fl","ag]"};
				
		model = new DefaultTableModel(rowData,title);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setWidth(40);
		//index
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setWidth(100);
		//id
		table.getColumnModel().getColumn(2).setMaxWidth(80);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(2).setWidth(80);
		//pw
		table.getColumnModel().getColumn(3).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setMinWidth(50);
		table.getColumnModel().getColumn(3).setWidth(50);
		//name
		table.getColumnModel().getColumn(4).setMaxWidth(70);
		table.getColumnModel().getColumn(4).setMinWidth(70);
		table.getColumnModel().getColumn(4).setWidth(70);
		//stuNum
		table.getColumnModel().getColumn(5).setMaxWidth(80);
		table.getColumnModel().getColumn(5).setMinWidth(80);
		table.getColumnModel().getColumn(5).setWidth(80);
		//major
		table.getColumnModel().getColumn(6).setMaxWidth(100);
		table.getColumnModel().getColumn(6).setMinWidth(100);
		table.getColumnModel().getColumn(6).setWidth(100);
		//ph
		table.getColumnModel().getColumn(7).setMaxWidth(50);
		table.getColumnModel().getColumn(7).setMinWidth(50);
		table.getColumnModel().getColumn(7).setWidth(50);
		//totallend
		table.getColumnModel().getColumn(8).setMaxWidth(50);
		table.getColumnModel().getColumn(8).setMinWidth(50);
		table.getColumnModel().getColumn(8).setWidth(50);
		//arr
		for(int i = 9; i < 14; i++){
			table.getColumnModel().getColumn(i).setMaxWidth(35);
			table.getColumnModel().getColumn(i).setMinWidth(35);
			table.getColumnModel().getColumn(i).setWidth(35);
		}
		for(int i = 14; i < 19; i++){
			table.getColumnModel().getColumn(i).setMaxWidth(48);
			table.getColumnModel().getColumn(i).setMinWidth(48);
			table.getColumnModel().getColumn(i).setWidth(48);
			}
		for(int i = 19; i < 24; i++){
			table.getColumnModel().getColumn(i).setMaxWidth(40);
			table.getColumnModel().getColumn(i).setMinWidth(40);
			table.getColumnModel().getColumn(i).setWidth(40);
		}
		
		js = new JScrollPane(table);
		js.setVisible(true);
		js.setBounds(20, 450, 1240, 250);
		
		
		layeredPan.add(back);
		layeredPan.add(reset);
		layeredPan.add(del);
		layeredPan.add(stu_num_);
		layeredPan.add(stu_num);
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AD_user();
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
	class fileWrite{
		fileWrite() throws IOException{
				try{
					BufferedWriter fw;
					fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/LB_User_Data.txt"));
					for(int i = 0; i < counter; i ++){
						fw.write(info[i].ID+" "+ info[i].PW+" "+ info[i].Name+" "+ info[i].stu_num + " " + info[i].aff +" "+ info[i].ph_Num + " "+ info[i].total_lend +" "+info[i].now_lend + " " + info[i].arrears+ " " +info[i].chk_master+ " " + info[i].lendBookNum[0]+ " " + info[i].lendBookNum[1]+  " " + info[i].lendBookNum[2]+  " " + info[i].lendBookNum[3]+  " " + info[i].lendBookNum[4] + " " + info[i].dateUntilBook[0]+ " " +info[i].dateUntilBook[1]+  " " +info[i].dateUntilBook[2]+  " " +info[i].dateUntilBook[3]+  " " +info[i].dateUntilBook[4] +" "+ info[i].extendtionFlag[0]+ " " +info[i].extendtionFlag[1]+  " " +info[i].extendtionFlag[2]+  " " +info[i].extendtionFlag[3]+  " " +info[i].extendtionFlag[4]+ "\n");
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
		String dateUntilBook[] = new String [5];
		int extendtionFlag[] = new int [5];
	}
@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource() == reset){
			for(int i = 0 ; i < counter; i++){
				if(info[i].stu_num.equals(stu_num.getText()) == true){
					info[i].extendtionFlag[0] = -2;
					info[i].extendtionFlag[1] = -2;
					info[i].extendtionFlag[2] = -2;
					info[i].extendtionFlag[3] = -2;
					info[i].extendtionFlag[4] = -2;
					try {
						new fileWrite();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,"초기화 완료");
				}
			}
		}else if( a.getSource() == del){
			for(int i = 0 ; i < counter; i++){
				if(info[i].stu_num.equals(stu_num.getText()) == true){
					for(int v = i ; v < counter; v ++){
						info[v] = info[v+1];
					}
					counter--;
					try {
						new fileWrite();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null,"삭제완료");
					break;
				}
			}
			
		}else if( a.getSource() == back){
			try {
				new AD_window();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispose();
			setVisible(false);
		}
		
	}
}
