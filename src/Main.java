import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Frame extends JFrame{

		/*JFrame fram1 = new JFrame();
		JFrame fram2 = new JFrame();*/
		JPanel pan = new JPanel();
		ImageIcon btn1 = new ImageIcon("img/btn1.png");
	    ImageIcon btn2 = new ImageIcon("img/btn2.png");
	    ImageIcon btn3 = new ImageIcon("img/btn3.png");
		JLabel title = new JLabel("소켓 프로그램");
	    JLabel ser = new JLabel("서버 : ");
		JLabel cli = new JLabel("클라이언트 : ");
	    JButton serbtn = new JButton(btn1);
	    JButton clibtn = new JButton(btn2);
	    JButton endbtn = new JButton(btn3);
	    
	public Frame() {
			add(pan);
			pan.setLayout(null);
			title.setBounds(100, 20, 400, 30);
			title.setFont(new Font("굴림", Font.PLAIN, 30));
			pan.add(title);
			ser.setBounds(100, 100, 70, 20);
			ser.setFont(new Font("굴림", Font.PLAIN, 20));
			cli.setBounds(100, 230, 130, 20);
			cli.setFont(new Font("굴림", Font.PLAIN, 20));
			pan.add(ser);
			pan.add(cli);
			pan.add(serbtn);
			serbtn.setBounds(100, 130, 210, 70);
			pan.add(clibtn);
			clibtn.setBounds(100, 260, 210, 70);
			pan.add(endbtn);
			endbtn.setBounds(100, 370, 210, 70);
	        setTitle("채팅 프로그램");
	        setSize(430, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	        
	        serbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new Server(); //서버 채팅창
					
				}
			});
	        clibtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new Client();
					
				}
			});
	        endbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
	    }
	
}


public class Main {

	public static void main(String[] args) {
		
		new Frame();
		
	}

}
