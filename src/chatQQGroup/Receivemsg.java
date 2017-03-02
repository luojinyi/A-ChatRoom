package chatQQGroup;

//�ͻ��˴�������߳���������Ϣ

import java.io.*;
import java.net.*;

public class Receivemsg implements Runnable{
	private DataInputStream dis;
	private boolean isRunning=true;
	//���캯������ʼ��dis
	public Receivemsg(Socket client){
		try {
			dis=new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			isRunning=false;
			CloseUtil.closeAll(dis);//�ر�����̵߳�������
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
