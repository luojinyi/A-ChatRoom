package chatQQGroup;

//客户端创建这个线程来发送信息

import java.io.*;
import java.net.*;

public class Sendmsg implements Runnable{
	private BufferedReader br;
	private DataOutputStream dos;
	private boolean isRunning=true;
	//构造函数
	public Sendmsg(Socket client){
		try {
			br=new BufferedReader(new InputStreamReader(System.in));//从控制台输入要发送的信息
			dos=new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while(isRunning){
			send();
		}
	}
	public void send(){
		String msg=getMsgFromConsole();//从控制台得到信息
		if(msg!=null&&msg!=""){
			try {
				//把信息发送给服务器
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				isRunning=false;
				CloseUtil.closeAll(dos,br);
				e.printStackTrace();
			}
		}
	}
	public String getMsgFromConsole(){
		try {
			 return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
