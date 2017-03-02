package chatQQGroup;

//客户端创建这个线程来接收信息

import java.io.*;
import java.net.*;

public class Receivemsg implements Runnable{
	private DataInputStream dis;
	private boolean isRunning=true;
	//构造函数，初始化dis
	public Receivemsg(Socket client){
		try {
			dis=new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			isRunning=false;
			CloseUtil.closeAll(dis);//关闭这个线程的所有流
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while(isRunning){
			System.out.println(receive());
		}
	}
	public String receive(){
		String msg="";
		try {
			msg=dis.readUTF();
		} catch (IOException e) {
			isRunning=false;
			CloseUtil.closeAll(dis);
			e.printStackTrace();
		}
		return msg;
	}
}
