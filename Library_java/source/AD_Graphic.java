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

	String DP[] = {"컴퓨터공학","멀티미디어","정보기술과","전기공학과","정보통신과","기계자동차","산업경영과","간호학과","유아교육과","다문화학과","복지학과"};
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
		
		//back = new JBtton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_나가기.png");
		
		
		layeredPan01.add(drawingPanel);
		layeredPan01.pack();
		layeredPan01.setVisible(true);
	}
	public static void main() throws IOException{
		new AD_Graphic();
	}

	//그래피를 그리는 패널 클래스
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
			g.drawString("단위 : 권",40,520);
			g.drawString("학과별 통계",450,530);
			for(int i = 0; i < 11; i++){
				g.setColor(Color.RED);
				g.fillRect(70+i*82,465,10,(int) ((-aff_count[i])*8));
			}
		}
	
		
	}
}






