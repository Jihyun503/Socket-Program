import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame{
	
	JPanel pan = new JPanel();
	JLabel title = new JLabel("접속할 채팅방 정보");
	JLabel ipL = new JLabel("ip :");
	JLabel portL = new JLabel("port :");
	JLabel myipL = new JLabel("내 ip :");
	JLabel nameL = new JLabel("내 이름 :");
	JTextField ipT = new JTextField();
	JTextField portT = new JTextField();
	JTextField myipT = new JTextField();
	JTextField nameT = new JTextField();
	JTextField chatT = new JTextField();
	JTextArea chat = new JTextArea();
	ImageIcon btn1 = new ImageIcon("img/btn6.png");
    ImageIcon btn2 = new ImageIcon("img/btn5.png");
    JButton openbtn = new JButton(btn1);
    JButton endbtn = new JButton(btn2);
    JButton namebtn = new JButton("변경");
    JButton submitbtn = new JButton("전송");
	String ip;
	int port;
	String name = "";
	Socket socket;
	
    Client(){
    	//화면 구성
		add(pan);
		pan.setLayout(null);
		title.setBounds(80, 20, 400, 25);
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
		
		myipL.setFont(new Font("굴림", Font.PLAIN, 15));
		myipL.setBounds(30, 160, 70, 15);
		pan.add(myipL);
		
		myipT.setBounds(90, 160, 150, 18);
		//내 ip 구하기
		try {
			myipT.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		//myipT.setEnabled(false);
		pan.add(myipT);
		
		nameL.setFont(new Font("굴림", Font.PLAIN, 15));
		nameL.setBounds(30, 180, 70, 15);
		pan.add(nameL);
		
		nameT.setBounds(90, 180, 130, 18);
		pan.add(nameT);
		
		namebtn.setBounds(250, 176, 65, 25);
		pan.add(namebtn);
		
		chat.setBounds(10, 210, 360, 280);
		pan.add(chat);
		
		chatT.setBounds(10, 510, 270, 18);
		pan.add(chatT);
		
		submitbtn.setBounds(290, 505, 65, 25);
		pan.add(submitbtn);
		
		//화면구성 끝
		
		openbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientThread clientThread = new ClientThread();

				clientThread.setDaemon(true);

				clientThread.start(); //
			}
		}); //서버접속버튼
		
		namebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				nameChange();
				
			}
		}); //이름변경버튼
		
		
		endbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		}); //종료버튼
		
		submitbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				
			}
		}); //전송버튼
		
		setBounds(1000, 0, 400, 600);
		setVisible(true);
		
		
	}
class ClientThread extends Thread { //서버 접속을 위한 스레드
		@Override
		public void run() {

			try {

				ip = ipT.getText(); 
				port = Integer.parseInt(portT.getText());
				name = ipT.getText(); 
				
				socket = new Socket(ip,port); //채팅창에 접속
				//채팅방 참여
				chat.append("*채팅방에 참여하였습니다*\n"); //서버 접속 완료를 알림

				BufferedReader bufferedReader = 
						new BufferedReader(new InputStreamReader(socket.getInputStream()));
				

				while(true) {//상대방 메시지 받기

					String msg = bufferedReader.readLine(); //메세지 받기
					if(msg == null) { //받아온 메세지가 null이면 다시 받아오기
						continue;
					}
					chat.append(msg + "\n");


				}

			} catch (UnknownHostException e) {

				chat.append("서버 주소가 이상합니다.\n");

			} catch (IOException e) {
				e.printStackTrace();
				chat.append("서버와 연결이 끊겼습니다.\n");

			}

		}
    }
    
    void sendMessage() {	

		String msg = name+": "+chatT.getText(); //채팅TextField에 써있는 글씨를 얻어오기

		chatT.setText(""); //입력 후 빈칸으로

		chat.append(msg + "\n");//채팅창에 표시


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
    
void nameChange() {	//이름 바꾸는 메소드

    Thread t = new Thread() {

    	@Override
    	public void run() {

    			try {
    				chat.append("*"+name+"님이 "+nameT.getText()+"(으)로 이름을 변경하였습니다*\n");
    				BufferedWriter bufferedWriter = 
    						new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    				bufferedWriter.write("*"+name+"님이 "+nameT.getText()+"(으)로 이름을 변경하였습니다*"); //얻어온 글씨를 wirte
    				name = nameT.getText(); //입력한 이름으로 변경
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
