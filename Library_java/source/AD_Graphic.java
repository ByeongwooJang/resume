package Library;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class AD_Graphic extends JFrame{
	JFrame layeredPan01;

	String DP[] = {"��ǻ�Ͱ���","��Ƽ�̵��","���������","������а�","������Ű�","����ڵ���","����濵��","��ȣ�а�","���Ʊ�����","�ٹ�ȭ�а�","�����а�"};
	JButton back;
	int aff_count[] = AD_window.aff_count;
	int selectMon = 0;
	
	@SuppressWarnings("deprecation")
	AD_Graphic() throws IOException{
		setTitle("Household Ledger Main");
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		layeredPan01 = new JFrame();
		layeredPan01.locate((windowSize.width - frameSize.width)/2,(windowSize.height - frameSize.height)/2);
		layeredPan01.setPreferredSize(new Dimension(1000,600));
						
		Container contentPane = layeredPan01.getContentPane();
		DrawingPanel drawingPanel = new DrawingPanel();
		drawingPanel.setBounds(40, 440, 400, 300);
		drawingPanel.setVisible(true);
		
		//back = new JBtton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_������.png");
		
		
		layeredPan01.add(drawingPanel);
		layeredPan01.pack();
		layeredPan01.setVisible(true);
	}
	public static void main() throws IOException{
		new AD_Graphic();
	}

	//�׷��Ǹ� �׸��� �г� Ŭ����
	class DrawingPanel extends JPanel{
		
		public void paint(Graphics g){
			g.clearRect(0,0,getWidth(),getHeight());
			for(int cnt = 0 ;cnt<12;cnt++){
				if(cnt == 0){
					g.drawString("0",25,505-40);
					g.drawLine(50, 505-40*(cnt+1), 950,505-40*(cnt+1));
				}else{
					g.drawString(cnt *5 +"",25,505-40*(cnt+1));
					g.drawLine(50, 505-40*(cnt+1), 950,505-40*(cnt+1));
				}
			}
			g.drawLine(50,20,50,480);
			for(int i = 0; i < 11; i ++)
					g.drawString(DP[i],70+i*80,490);
			g.drawString("���� : ��",40,520);
			g.drawString("�а��� ���",450,530);
			for(int i = 0; i < 11; i++){
				g.setColor(Color.RED);
				g.fillRect(70+i*82,465,10,(int) ((-aff_count[i])*8));
			}
		}
	
		
	}
}






