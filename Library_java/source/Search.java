package Library;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class Search extends JFrame implements ActionListener,ItemListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLayeredPane layeredPan;
	BufferedImage img = null;
	JButton search_cate, search_detail, back, lend;
	JLabel 카테고리검색, 상세검색, 책번호, bookName,writer,madeBy;
	JComboBox<String> cmbBox1, cmbBox2;
	JTextField txt_bookName, txt_writer, txt_madeBy,txt_bookNum;
	JRadioButton and01 = new JRadioButton("And",true);
	JRadioButton or01 = new JRadioButton("Or");
	JRadioButton and02 = new JRadioButton("And_",true);
	JRadioButton or02 = new JRadioButton("Or_");
	JTable table;
	DefaultTableModel model;
	JScrollPane js;
	SimpleDateFormat DD = new SimpleDateFormat("yy-MM-dd");
	Calendar cal = new GregorianCalendar(Locale.KOREA);
	
	bookData Book[] = new bookData[10000];
	total total[] = new total[10000];
	static info info[] = new info[1000];
	
	final int lend_flag_flag = AD_window.lend_flag_flag;
	final int user_num = LB_login.user_num;	
	final String stu_number = LB_login.stu_number;
	int chk_AO_01 = 1;
	int chk_AO_02 = 1;
	int counter = 0;
	int counter_ = 0;
	int counter__ = 0;
	int cti = 0;
	int lendBook = -1;
	static int lend_flag = 0;
	

	String AD_location[] = new String[4];
	String strCateCombo[] = new String[2];
	String bookCate01[] = {"Choose","총류","철학","종교","사회과학","순수과학","기술과학","예술","언어","문학","역사"};
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
	
	Search() throws IOException{
		if(lend_flag_flag == 1)
			lend_flag = 0;
		new fileRead();
		new fileRead1();
		new fileRead1_();
		new fileRead1__AD();
		
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
			img = ImageIO.read(new File("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/ad배경.png"));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,1280,720);
		
		back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_나가기_2.png"));
		back.setBounds(20,115,120,50);
		back.addActionListener(this);
		
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		
		카테고리검색 = new JLabel("카테고리검색");
		카테고리검색.setBounds(20, 150, 200, 80);
		카테고리검색.setFont(LB_font);
		
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
		

		cmbBox2.setBounds(350,175,140,30);
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
		cmbBox1.setBounds(200,175,140,30);
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
		
		cmbBox1.setFont(TX_font);
		cmbBox2.setFont(TX_font);
		
		search_cate = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_bookSearch.png"));
		search_cate.setBounds(520,165,120,50);
		search_cate.addActionListener(this);
		
		search_cate.setBorderPainted(false);
		search_cate.setFocusPainted(false);
		search_cate.setContentAreaFilled(false);
		
		상세검색 = new JLabel("상세검색");
		상세검색.setBounds(20, 210, 200, 80);
		상세검색.setFont(LB_font);
		
		ButtonGroup gr01 = new ButtonGroup();
		gr01.add(and01);
		gr01.add(or01);
		
		and01.addItemListener(this);
		or01.addItemListener(this);
		and01.setBounds(450, 245, 50, 15);
		or01.setBounds(500, 245, 40, 15);
		
		ButtonGroup gr02 = new ButtonGroup();
		gr02.add(and02);
		gr02.add(or02);
		
		and02.addItemListener(this);
		or02.addItemListener(this);
		and02.setBounds(760, 245, 60, 15);
		or02.setBounds(815, 245, 50, 15);
		
		bookName = new JLabel("책 제목");
		bookName.setBounds(150, 210, 150, 80);
		bookName.setFont(LB_font2);
		
		txt_bookName = new JTextField(100);
		txt_bookName.setFont(TX_font);
		txt_bookName.setBounds(230,235,200,30);
		
		writer = new JLabel("저자");
		writer.setBounds(550, 210, 150, 80);
		writer.setFont(LB_font2);
		
		txt_writer = new JTextField(100);
		txt_writer.setFont(TX_font);
		txt_writer.setBounds(600,235,150,30);
		
		madeBy = new JLabel("출판사");
		madeBy.setBounds(880, 210, 150, 80);
		madeBy.setFont(LB_font2);
		
		txt_madeBy = new JTextField(100);
		txt_madeBy.setFont(TX_font);
		txt_madeBy.setBounds(950,235,150,30);
		
		search_detail = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_bookSearch.png"));
		search_detail.setBounds(1120,225,120,50);
		search_detail.addActionListener(this);
		
		search_detail.setBorderPainted(false);
		search_detail.setFocusPainted(false);
		search_detail.setContentAreaFilled(false);
		
		책번호 = new JLabel("책 번호");
		책번호.setBounds(875, 260, 150, 80);
		책번호.setFont(LB_font2);
		
		txt_bookNum = new JTextField(10);
		txt_bookNum.setFont(TX_font);
		txt_bookNum.setBounds(950,285,130,30);
		txt_bookNum.setHorizontalAlignment(JTextField.RIGHT);
		
		lend = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_lend.png"));
		lend.setBounds(1100,270,150,60);
		lend.addActionListener(this);
		
		lend.setBorderPainted(false);
		lend.setFocusPainted(false);
		lend.setContentAreaFilled(false);
		
		Advertisement AD = new Advertisement();
		AD.setLayout(null);
		AD.setBounds(0,0,1280,100);
		AD.setOpaque(false);
		new Thread(AD).start();
		
		ClockMessage clockMessage = new ClockMessage();
		clockMessage.setBounds(1120,660,220,40);
		clockMessage.setOpaque(false);
		new Thread(clockMessage).start();
		
		for( int i = 0 ; i < counter_ ; i++){
			System.out.println(i+ "  " +info[i].ID +"  " + info[i].Name+info[i].lendBookNum[0]+info[i].lendBookNum[1]+info[i].lendBookNum[2]+info[i].lendBookNum[3]+info[i].lendBookNum[4]);
		}

		
		layeredPan.add(lend);
		layeredPan.add(txt_bookNum);
		layeredPan.add(책번호);
		layeredPan.add(clockMessage);
		layeredPan.add(back);
		layeredPan.add(search_detail);
		layeredPan.add(txt_madeBy);
		layeredPan.add(madeBy);
		layeredPan.add(txt_writer);
		layeredPan.add(writer);
		layeredPan.add(and02);
		layeredPan.add(or02);
		layeredPan.add(and01);
		layeredPan.add(or01);
		layeredPan.add(txt_bookName);
		layeredPan.add(bookName);
		layeredPan.add(AD);
		layeredPan.add(search_cate);
		layeredPan.add(상세검색);
		layeredPan.add(카테고리검색);
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
		if(ee.getSource() == search_detail){
			String str_book, str_writer,str_madeBy;
			
			str_book = txt_bookName.getText();
			str_writer = txt_writer.getText();
			str_madeBy = txt_madeBy.getText();
			
			int t = 0;
			cti = 0;
			
			for(cti = 0; cti < counter; cti++)
				if(chk_AO_01 == 1 && chk_AO_02 == 1){
					if(Book[cti].bookName.equals(str_book) && Book[cti].writer.equals(str_writer)&& Book[cti].madeBy.equals(str_madeBy)){
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
						}
						t++;
					}
				}else if(chk_AO_01 == 1 && chk_AO_02 == 0){
					if(Book[cti].bookName.equals(str_book) && Book[cti].writer.equals(str_writer)|| Book[cti].madeBy.equals(str_madeBy)){
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
						}
						t++;
					}
				}else if(chk_AO_01 == 0 && chk_AO_02 == 1){
					if(Book[cti].bookName.equals(str_book) || Book[cti].writer.equals(str_writer) && Book[cti].madeBy.equals(str_madeBy)){
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
						}
						t++;
					}
				}else if(chk_AO_01 == 0 && chk_AO_02 == 0){
					if(Book[cti].bookName.equals(str_book) || Book[cti].writer.equals(str_writer)|| Book[cti].madeBy.equals(str_madeBy)){
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
				}
			
			System.out.println(str_book+ " " + chk_AO_01+" "+str_writer + " "+chk_AO_02+" " + str_madeBy);
			t = 0;
		}else if(ee.getSource() == search_cate){
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
			new User_window();
		}else if( ee.getSource() == lend){
			int i, chk= -1;
			String str_bookNum;
			str_bookNum = txt_bookNum.getText();
			
			for( i = 0; i <5; i ++){
				if(info[user_num].lendBookNum[i] == -1){
					chk = 1;
					break;
				}else{
					chk = 2;
				}
			}
			for(i = 0; i < counter;i++){
				if(Book[i].bookNum.equals(str_bookNum)&& Book[i].lend == 0&&chk == 1){
					Book[i].lend = 1;
					lendBook = i;
					break;
				}
			}
			if(lendBook == -1&& chk == 2){
				JOptionPane.showMessageDialog(null,"Warning! 최대 5권 빌릴수 있습니다.");
			}else 	if(lendBook == -1){
				JOptionPane.showMessageDialog(null,"존재하지 않거나 대출중입니다.");
			}
			for(int a = 0; a < 5; a++){
				if(lendBook == -1&& chk == 2){
					break;
				}
				if(info[user_num].lendBookNum[a] == -1 && chk == 1 && lendBook != -1){
					info[user_num].lendBookNum[a] = lendBook;
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_YEAR, 7);
					
					info[user_num].dateUntilBook[a] = DD.format(cal.getTime());
					
					total[counter__] = new total();
					total[counter__].stu_num = stu_number;
					total[counter__].aff = info[user_num].aff;
					total[counter__].cate01 = Book[lendBook].cate01;
					counter__++;
					
					lend_flag = 1;
					
					JOptionPane.showMessageDialog(null,"대출완료");
					info[user_num].total_lend++;
					try {
						new fileWrite__();
						new fileWrite();
						new fileWrite_book();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}else if(info[user_num].lendBookNum[4] != -1){
					System.out.println(info[user_num].lendBookNum[4]);
					JOptionPane.showMessageDialog(null,"Warning! 최대 5권 빌릴수 있습니다.");
					break;
				}
			}
		}		
		lendBook = -1;
		
		
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
	js.setBounds(40, 350, 1100, 250);
		
	layeredPan.add(js);
	}

	public void itemStateChanged(ItemEvent e) {
		AbstractButton sel = (AbstractButton)e.getItemSelectable();
		if(e.getStateChange() == ItemEvent.SELECTED){
			if(sel.getText().equals("And")){
				chk_AO_01 = 1;
			}else if(sel.getText().equals("Or")){
				chk_AO_01 = 0;
			}else if(sel.getText().equals("And_")){
				chk_AO_02 = 1;
			}else if(sel.getText().equals("Or_")){
				chk_AO_02 = 0;
			}
		}
		
	}
	class Advertisement extends JPanel implements Runnable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Image img[] = new Image[5];
		int i = 1;
		public Advertisement(){
			img[1] = Toolkit.getDefaultToolkit().createImage(AD_location[0]);
			img[2] = Toolkit.getDefaultToolkit().createImage(AD_location[1]);
			img[3] = Toolkit.getDefaultToolkit().createImage(AD_location[2]);
			img[4] = Toolkit.getDefaultToolkit().createImage(AD_location[3]);
			img[0] = img[1];
		}
		public void paint(Graphics g){
			super.paint(g);
			g.drawImage(img[0], 0, 0, this);
		}
		public void run() {
			while(true){
				try{
					Thread.sleep(3000);
					switch(i){
					case 1:
						img[0] = img[i];
						i++;
						repaint();
						break;
					case 2:
						img[0] = img[i];
						i++;
						repaint();
						break;
					case 3:
						img[0] = img[i];
						i++;
						repaint();
						break;
					case 4:
						img[0] = img[i];
						i=1;
						repaint();
						break;
					}
				} catch(InterruptedException e){
					e.printStackTrace();
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
	                   	                    
	                    counter__++;
	                    i = 0;
	                    
	                }
	                
	            }catch(Exception ex){
	    	           System.out.println("ERROR!!bbb"); 
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
	class fileWrite__{
		fileWrite__() throws IOException{
			
			try{
					BufferedWriter fw;
					fw = new BufferedWriter(new FileWriter("C:/Users/aolo26698824/workspace/JAVA/src/total.txt"));
						for(int i = 0; i < counter__; i ++){
							fw.write(total[i].stu_num + " " + total[i].aff + " " + total[i].cate01+"\n");
						}
						
					fw.close();
					System.out.println("WRITED");
				}catch(Exception ex){
					ex.printStackTrace();
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
		String dateUntilBook[] = new String [5];
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
