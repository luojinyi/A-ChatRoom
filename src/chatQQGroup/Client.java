package chatQQGroup;
// �ͻ���
import java.io.IOException;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		startUp();
	}
	public static void startUp(){
		try {
			Socket client=new Socket("localhost",6666);
			System.out.println("***�ͻ���1��***");
			/*
			 * �ֱ𴴽����պͷ�����Ϣ���߳�
			 */
			new Thread(new Receivemsg(client)).start();
			new Thread(new Sendmsg(client)).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
