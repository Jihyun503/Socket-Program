import java.net.*;
import java.io.*;
public class UDPClientExam{
	public static void main(String[] args) throws Exception{
	  
	System.out.print("보낼 메시지 : ");
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String msg = in.readLine();
	DatagramPacket dp = new DatagramPacket(msg.getBytes(),
        msg.getBytes().length, InetAddress.getByName("127.0.0.1"),3000);
	DatagramSocket ds = new DatagramSocket();
	ds.send(dp);
       }
}