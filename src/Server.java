import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame{
	
	JPanel pan = new JPanel();
	JLabel title = new JLabel("채팅방 정보");
	JLabel ipL = new JLabel("ip :");
	JLabel portL = new JLabel("port :");
	JLabel nameL = new JLabel("내 이름 :");
	JTextField ipT = new JTextField();
	JTextField portT = new JTextField();
	JTextField nameT = new JTextField();
	JTextField chatT = new JTextField();
	JTextArea chat = new JTextArea();
	ImageIcon btn1 = new ImageIcon("img/btn4.png");
    ImageIcon btn2 = new ImageIcon("img/btn5.png");
    JButton openbtn = new JButton(btn1);
    JButton endbtn = new JButton(btn2);
    JButton namebtn = new JButton("변경");
    JButton submitbtn = new JButton("전송");
    int openchk = 1;
	String ip;
	int port;
	String name = "";
	ServerSocket serverSocket;
	Socket socket;
	
    Server(){
    	
		add(pan);
		pan.setLayout(null);
		
		title.setBounds(120, 20, 400, 25);
		title.setFont(new Font("굴림", Font.PLAIN, 25));
		pan.add(title);
		
		ipL.setFont(new Font("굴림", Font.PLAIN, 15));
		ipL.setBounds(30, 70, 30, 15);
		pan.add(ipL);	
		portL.setFont(new Font("굴림", Font.PLAIN, 15));
		portL.setBounds(30, 120, 41, 15);
		pan.add(portL);
		
		ipT.setBounds(60, 70, 130, 18);
		pan.add(ipT);
		portT.setBounds(70, 120, 130, 18);
		pan.add(portT);
		
		openbtn.setBounds(220, 70, 65, 65);
		pan.add(openbtn);
		endbtn.setBounds(300, 70, 65, 65);
		pan.add(endbtn);
		
		nameL.setFont(new Font("굴림", Font.PLAIN, 15));
		nameL.setBounds(30, 180, 70, 15);
		pan.add(nameL);
		
		nameT.setBounds(90, 180, 130, 18);
		pan.add(nameT);
		
		namebtn.setBounds(250, 176, 65, 25);
		pan.add(namebtn);
		
		chat.setBounds(10, 210, 360, 280);
		//chat.setEnabled(false);
		pan.add(chat);
		
		chatT.setBounds(10, 510, 270, 18);
		pan.add(chatT);
		
		submitbtn.setBounds(290, 505, 65, 25);
		pan.add(submitbtn);

		openbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ServerThread serverThread = new ServerThread();

				serverThread.setDaemon(true); //메인 끝나면 같이 종료

				serverThread.start();
				
			}
		});
		
		endbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		
		namebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nameChange();
				
			}
		});
		
		submitbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sendMessage();
    			
			}
		});

		
		setBounds(500, 0, 400, 600);
		setVisible(true);
	}
    
    class ServerThread extends Thread { //서버를 열기 위한 스레드

    	@Override

    	public void run() {			

    		try {  //서버 소켓 생성 작업
    			
    			ip = ipT.getText();
				port = Integer.parseInt(portT.getText());
				name = ipT.getText();
				
    			serverSocket = new ServerSocket(port);

    			chat.append("*참여자를 기다리는 중입니다*\n");

    			socket = serverSocket.accept();//클라이언트가 접속할때까지 커서(스레드)가 대기

    			chat.append(socket.getInetAddress().getHostAddress() + "님이 접속하셨습니다.\n");

    			BufferedReader bufferedReader = 
						new BufferedReader(new InputStreamReader(socket.getInputStream()));

    			while(true) {//상대방 메시지 받기

					String msg = bufferedReader.readLine(); //메세지 받기
					if(msg == null) { //받아온 메세지가 null이면 다시 받아오기
						continue;
					}
					chat.append(msg + "\n");

					//chat.setCaretPosition(chat.getText().length());

				}			

    			

    		} catch (IOException e) {

    		}

    	}

    }
    
void sendMessage() {	

    	String msg = name+": "+chatT.getText(); //채팅TextField에 써있는 글씨를 얻어오기

		chatT.setText(""); //입력 후 빈칸으로

		chat.append(msg + "\n");//채팅창에 표시

		//chat.setCaretPosition(chat.getText().length());

		Thread t = new Thread() {

			@Override

			public void run() {

				try {
					BufferedWriter bufferedWriter = 
							new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					bufferedWriter.write(msg); //얻어온 글씨를 wirte
					bufferedWriter.newLine(); //한줄끝
					bufferedWriter.flush();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		};		

		t.start();

	}

void nameChange() {	

	Thread t = new Thread() {

		@Override

		public void run() {

			try {
				chat.append("*"+name+"님이 "+nameT.getText()+"(으)로 이름을 변경하였습니다*\n");
				BufferedWriter bufferedWriter = 
						new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				bufferedWriter.write("*"+name+"님이 "+nameT.getText()+"(으)로 이름을 변경하였습니다*"); //얻어온 글씨를 wirte
				name = nameT.getText();
				bufferedWriter.newLine(); //한줄끝
				bufferedWriter.flush();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	};		

	t.start();

}
    
}

