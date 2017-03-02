package chatQQGroup;
// 客户端
import java.io.IOException;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		startUp();
	}
	public static void startUp(){
		try {
			Socket client=new Socket("localhost",6666);
			System.out.println("***客户端1号***");
			/*
			 * 分别创建接收和发送信息的线程
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
