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
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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


public class AD_Book_InsertDel extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JButton search_cate, back, add, del;
	JComboBox<String> cmbBox1, cmbBox2;
	JTextField txt_cate1, txt_cate2, txt_bookNum , txt_bookName, txt_writen, txt_madeBy;
	JTable table;
	DefaultTableModel model;
	JScrollPane js;
	
	bookData Book[] = new bookData[10000];

	int counter = 0;
	int cti = 0;


	String strCateCombo[] = new String[2];
	String strCateCombo2[] = new String[2];
	String bookCate01[] = {"Choose","총류","철학","종교","사회과학","순수과학","기술과학","예술","언어","문학","역사"};
	String bookCate_second_all[] = {"시스템","전산학","프로그래밍","도서학","문헌정보학","백과사전","강연집,수필집","일반연속간행물","일반학회,단체","신문,언론","일반전집","향토자료","형이상학",
			"철학의체계","경학","동양철학","서양철학","논리학","심리학","윤리학","비종교","불교","기독교","도교","천도교","신도","인도교","회교","기타","사회사상","통계학","경제학","사회학","정치학",
			"행정학","법학","교육학","민속학","군사학","과학이론","수학","물리학","화학","천문학","지학","광물학","생명과학","식물학", "동물학","의학","농학","공학","건축공학","기계공학","전기,전자공학",
			"화학공학","제조업","가정학","미학","건축술","조각","공예","서예","도화","사진술","음악","연극","오락","한국어","중국어","일본어","영어","독일어","프랑스어","스페인어","이탈리아어","기타",
			"한국문학","중국문학","일본문학","영미문학","독일문학","프랑스문학","스페인문학","이탈리아문학","기타문학","아시아","유럽","아프리카","아메리카","남아메리카","오세아니아","양극지방","지리","전기"};
	
	String bookCate01_01[] = {"Choose","시스템","전산학","프로그래밍","도서학","문헌정보학","백과사전","강연집,수필집","일반연속간행물","일반학회,단체","신문,언론","일반전집","향토자료"};
	String bookCate01_02[] = {"Choose","형이상학","철학의체계","경학","동양철학","서양철학","논리학","심리학","윤리학"};
	String bookCate01_03[] = {"Choose","비종교","불교","기독교","도교","천도교","신도","인도교","회교","기타"};
	String bookCate01_04[] = {"Choose","사회사상","통계학","경제학","사회학","정치학","행정학","법학","교육학","민속학","군사학"};
	String bookCate01_05[] = {"Choose","과학이론","수학","물리학","화학","천문학","지학","광물학","생명과학","식물학", "동물학"};
	String bookCate01_06[] = {"Choose","의학","농학","공학","건축공학","기계공학","전기,전자공학","화학공학","제조업","가정학"};
	String bookCate01_07[] = {"Choose","미학","건축술","조각","공예","서예","도화","사진술","음악","연극","오락"};
	String bookCate01_08[] = {"Choose","한국어","중국어","일본어","영어","독일어","프랑스어","스페인어","이탈리아어","기타"};
	String bookCate01_09[] = {"Choose","한국문학","중국문학","일본문학","영미문학","독일문학","프랑스문학","스페인문학","이탈리아문학","기타문학"};
	String bookCate01_10[] = {"Choose","아시아","유럽","아프리카","아메리카","남아메리카","오세아니아","양극지방","지리","전기"};
	Hashtable<String, String[]> subItems = new Hashtable<String, String[]>();
		
	Font LB_font = new Font("맑은고딕",Font.BOLD,25);
	Font LB_font2 = new Font("맑은고딕",Font.BOLD,20);
	Font TX_font = new Font("맑은고딕",Font.BOLD,16);
	
	AD_Book_InsertDel() throws IOException{
	
		new fileRead();

		
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Admin_back3.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		back = new JButton();
		back.setBounds(50,65,120,50);
		back.addActionListener(this);
		
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		
		cmbBox2 = new JComboBox<String>();
		cmbBox2.setPrototypeDisplayValue("XXXXX");
		subItems.put(bookCate01[1],bookCate01_01);
		subItems.put(bookCate01[2],bookCate01_02);
		subItems.put(bookCate01[3],bookCate01_03);
		subItems.put(bookCate01[4],bookCate01_04);
		subItems.put(bookCate01[5],bookCate01_05);
		subItems.put(bookCate01[6],bookCate01_06);
		subItems.put(bookCate01[7],bookCate01_07);
		subItems.put(bookCate01[8],bookCate01_08);
		subItems.put(bookCate01[9],bookCate01_09);
		subItems.put(bookCate01[10],bookCate01_10);
		

		cmbBox2.setBounds(350,165,140,30);
		//define multiple combobox
		cmbBox1 = new JComboBox<String>(bookCate01);
		cmbBox1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String item = (String)cmbBox1.getSelectedItem();
				Object o = subItems.get(item);
				
				if(o == null){
					cmbBox2.setModel(new DefaultComboBoxModel<String>());
				} else{
					cmbBox2.setModel(new DefaultComboBoxModel<String>((String[])o));
				}
			}
			
		});
		cmbBox1.setBounds(170,165,140,30);
		//store selected string
		cmbBox1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				strCateCombo[0] = (String) cmbBox1.getSelectedItem();
			}
		});
		cmbBox2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				strCateCombo[1] = (String) cmbBox2.getSelectedItem();
			}
		});
		
		
		search_cate = new JButton();
		search_cate.setBounds(520,165,100,40);
		search_cate.addActionListener(this);
		
		search_cate.setBorderPainted(false);
		search_cate.setFocusPainted(false);
		search_cate.setContentAreaFilled(false);
		
		txt_bookNum  = new JTextField(14);
		txt_bookNum.setFont(TX_font);
		txt_bookNum.setBounds(155, 230,150,30);
		txt_bookNum.setOpaque(false);
		txt_bookNum.setForeground(Color.white);
		
		txt_cate1  = new JTextField(14);
		txt_cate1.setFont(TX_font);
		txt_cate1.setBounds(155, 300,130,30);
		txt_cate1.setOpaque(false);
		txt_cate1.setForeground(Color.white);
		
		txt_cate2  = new JTextField(14);
		txt_cate2.setFont(TX_font);
		txt_cate2.setBounds(385, 300,130,30);
		txt_cate2.setOpaque(false);
		txt_cate2.setForeground(Color.white);
		
		txt_bookName = new JTextField(40);
		txt_bookName.setFont(TX_font);
		txt_bookName.setBounds(155,375,203,30);
		txt_bookName.setOpaque(false);
		txt_bookName.setForeground(Color.white);
		
		txt_writen = new JTextField(20);
		txt_writen.setFont(TX_font);
		txt_writen.setBounds(155,445,130,30);
		txt_writen.setOpaque(false);
		txt_writen.setForeground(Color.white);
		
		txt_madeBy = new JTextField(20);
		txt_madeBy.setFont(TX_font);
		
		txt_madeBy.setBounds(400,445,150,30);
		txt_madeBy.setOpaque(false);
		txt_madeBy.setForeground(Color.white);
			
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,660,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		add = new JButton();
		add.setBounds(570,445,60,40);
		add.addActionListener(this);
		
		add.setBorderPainted(false);
		add.setFocusPainted(false);
		add.setContentAreaFilled(false);
		
		del = new JButton();
		del.setBounds(650,445,60,40);
		del.addActionListener(this);
		
		del.setBorderPainted(false);
		del.setFocusPainted(false);
		del.setContentAreaFilled(false);
		
		
		layeredPan.add(del);
		layeredPan.add(add);
		layeredPan.add(txt_cate2);
		layeredPan.add(txt_cate1);
		layeredPan.add(clockMessage);
		layeredPan.add(txt_bookNum);
		layeredPan.add(txt_bookName);
		layeredPan.add(txt_writen);
		layeredPan.add(txt_madeBy);
		layeredPan.add(back);
		layeredPan.add(search_cate);
		layeredPan.add(cmbBox1);
		layeredPan.add(cmbBox2);
		layeredPan.add(pn);
		add(layeredPan);
		setUndecorated(true);
		setVisible(true);
	}

	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public static void main() throws IOException {
		new Search();
	}
	
	public void actionPerformed(ActionEvent ee) {
		// TODO Auto-generated method stub
		String[][] rowData = new String[counter][7];
		
		if(ee.getSource() == search_cate){
			int t = 0;
			for(int cti = 0; cti < counter;cti ++)
			if(Book[cti].cate01.equals(strCateCombo[0]) && Book[cti].cate02.equals(strCateCombo[1])){
				System.out.println(Book[cti].bookName.equals(strCateCombo[0])+" "+ Book[cti].bookName.equals(strCateCombo[1]));
				for(int j = 0; j < 7; j++){
					switch(j){
					case 0:
						rowData[t][j] = Book[cti].cate01;					
						break;
					case 1:
						rowData[t][j] = Book[cti].cate02;
						break;
					case 2:
						rowData[t][j] = String.valueOf(Book[cti].bookNum);
						break;
					case 3:
						rowData[t][j] = Book[cti].bookName;
						break;
					case 4:
						rowData[t][j] = Book[cti].writer;
						break;
					case 5:
						rowData[t][j] = Book[cti].madeBy;
						break;
					case 6:
						if(Book[cti].lend == 1)
							rowData[t][j] = "대출중";
						else
							rowData[t][j] = "대출가능";
						break;
					}
				}t++;
			}
		}else if(ee.getSource() == back){
			setVisible(false); // 현재 프레임을 안보이게함
			dispose();
			try {
				new AD_window();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if( ee.getSource() == add){
			int chk = -1;
			for(int i = 1; i < 11; i++){
				if(bookCate01[i].equals(txt_cate1.getText()) == true){
					for(int j = 0; j < 96; j++){
						if(bookCate_second_all[j].equals(txt_cate2.getText()) == true){
							JOptionPane.showMessageDialog(null,"카테고리 검사 완료!");
							chk = 1;
							break;
						}
					}
				}
			}
			for(int z = 0; z < counter; z++){
				if(Book[z].bookNum.equals(txt_bookNum.getText()) == true){
					JOptionPane.showMessageDialog(null,"책번호 중복 오류!");
					chk = -1;
					break;
				}
			}
			if(chk == 1){
				Book[counter] = new bookData();
				Book[counter].bookNum = txt_bookNum.getText();
				Book[counter].cate01 = txt_cate1.getText();
				Book[counter].cate02 = txt_cate2.getText();
				Book[counter].bookName = txt_bookName.getText();
				Book[counter].writer = txt_writen.getText();
				Book[counter].madeBy = txt_madeBy.getText();
				Book[counter].lend = 0;
				
				counter ++;
				try {
					new fileWrite_book();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null,"카테고리 검사 에러!");
			}
			
		}else if( ee.getSource() == del){
			int k;
			for(int i = 0; i < counter; i++){
				if(Book[i].bookNum.equals(txt_bookNum.getText()) == true){
					for(k = i; k < counter; k++){
						Book[k] = Book[k+1];
					}
					Book[k] = null;
					counter --;
					break;
				}
			}
			try {
				new fileWrite_book();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"삭제 완료!");
		}
			
		String[] title = {"카테고리01",
				"카테고리02",
				"책 번호",
				"책 제목",
				"저자",
				"출판사",
				"대출상태",
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
	//earn , expense
	table.getColumnModel().getColumn(4).setMaxWidth(150);
	table.getColumnModel().getColumn(4).setMinWidth(150);
	table.getColumnModel().getColumn(4).setWidth(150);
	//credit cash
	table.getColumnModel().getColumn(5).setMaxWidth(150);
	table.getColumnModel().getColumn(5).setMinWidth(150);
	table.getColumnModel().getColumn(5).setWidth(150);
	//memo
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
	js.setBounds(60, 520, 1100, 120);
	layeredPan.add(js);
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
	class bookData{
		String cate01;
		String cate02;
		String bookNum;
		String bookName;
		String writer;
		String madeBy;
		int lend;
	}

}
