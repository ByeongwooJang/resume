package Library;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Library.LB_login.MyPanel;


public class bookInfo extends JFrame implements ActionListener{
	Image img[] = new Image[7];
	JLayeredPane layeredPan;
	BufferedImage imge = null;
	public bookInfo(int num){
		String tmp = null;
		
		setTitle("Book Infomation");
		setSize(850,570);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width)/2,(windowSize.height - frameSize.height)/2);
		
		//layout setting
		setLayout(null);
		layeredPan = new JLayeredPane();
		layeredPan.setBounds(0,0,850,570);
		layeredPan.setLayout(null);
		switch(num){
			case 0:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_¶ó¼î¸ù.png";
				break;
			case 1:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_¹é¹üÀÏÁö.png";
				break;
			case 2:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_ÀÌ±âÀûÀÎ À¯ÀüÀÚ.png";
				break;
			case 3:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_ÀÚº»ÁÖÀÇ4.0.png";
				break;
			case 4:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_Á×ÀÌ´Â Ã¥.png";
				break;
			case 5:
				tmp = "C:/Users/aolo26698824/workspace/JAVA/src/Library_image/Txt_ÃÑ,±Õ,¼è.png";
				break;
		}
		
		try {
			imge = ImageIO.read(new File(tmp));
		} catch (Exception e) {
			System.out.println("Load error");
			System.exit(0);
		}
		
		MyPanel pn = new MyPanel();
		pn.setBounds(0,0,800,570);
		
		JButton back = new JButton(new ImageIcon("C:/Users/aolo26698824/workspace/JAVA/src/Library_image/btn_³ª°¡±â.png"));
		back.setBounds(700,480,100,50);
		back.addActionListener(this);
		
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setContentAreaFilled(false);
		
		layeredPan.add(back);
		layeredPan.add(pn);
		add(layeredPan);
		setUndecorated(true);
		setVisible(true);

	}
	class MyPanel extends JPanel{
		public void paint(Graphics g){
			g.drawImage(imge, 0, 0, null);
		}
	}
	public static void main(int num){
		
		new bookInfo(num);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new User_window();
		dispose();
		setVisible(false);
	}
}
