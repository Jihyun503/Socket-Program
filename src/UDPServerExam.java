import java.net.*;
import java.io.*;
public class UDPServerExam{
	public static void main(String[] args) throws Exception{
	  
	  DatagramSocket ds= new DatagramSocket(3000);
	  byte[] data=new byte[65508];
	  DatagramPacket dp= new DatagramPacket(data, data.length);
      System.out.println("UDP 서버 대기중");
	  ds.receive(dp);
	  String msg = new String(dp.getData()).trim();
	  System.out.println(" 보낸 주소  :"+dp.getAddress());
	  System.out.println(" 보낸메시지 : " + msg);
	
	/*try {
        DatagramSocket ds = new DatagramSocket(port);
        while (true) {
            byte buffer[] = new byte[512];
            DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
            System.out.println("ready");
            ds.receive(dp);
            String str = new String(dp.getData());
            System.out.println("수신된 데이터 : " + str);

            InetAddress ia = dp.getAddress();
            port = dp.getPort();
            System.out.println("client ip : " + ia + " , client port : " + port);
            dp = new DatagramPacket(dp.getData(),dp.getData().length, ia,port);
            ds.send(dp);
        }
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }*/
	}
}