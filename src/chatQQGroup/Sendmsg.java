package chatQQGroup;

//�ͻ��˴�������߳���������Ϣ

import java.io.*;
import java.net.*;

public class Sendmsg implements Runnable{
	private BufferedReader br;
	private DataOutputStream dos;
	private boolean isRunning=true;
	//���캯��
	public Sendmsg(Socket client){
		try {
			br=new BufferedReader(new InputStreamReader(System.in));//�ӿ���̨����Ҫ���͵���Ϣ
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
		String msg=getMsgFromConsole();//�ӿ���̨�õ���Ϣ
		if(msg!=null&&msg!=""){
			try {
				//����Ϣ���͸�������
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
