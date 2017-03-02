package chatQQGroup;

/*
 * ��������
 * ����ÿ���ͻ��˷��͵���Ϣ
 * Ȼ�����Ϣ�������������������Ŀͻ���
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	//��һ��List���������ÿ���ͻ��˶�Ӧ�ķ������߳�
	private List<serverThread> all=new ArrayList<serverThread>();
	int count=0;//��������ͻ�����
	public static void main(String[] args) {
		new Server().startUp();
	}
	
	@SuppressWarnings("resource")
	public void startUp(){
		try {
			//���ü����˿�
			ServerSocket server=new ServerSocket(6666);
			System.out.println("***********��������************");
			while(true){
				//�����ͻ���
				Socket client=server.accept();
				count++;
				//���̴߳���
				new Thread(new serverThread(client)).start();
				System.out.println("�Ѿ���"+count+"λͬѧ��Ⱥ������");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class serverThread implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning=true;
		//���캯������ʼ���������������
		public serverThread(Socket client){
			try {
				dis=new DataInputStream(client.getInputStream());
				dos=new DataOutputStream(client.getOutputStream());
				all.add(this);//���̼߳��뵽������ȥ
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(isRunning){
				//Ⱥ��
				sentGroup();
			}
		} 
		public void sentGroup(){//Ⱥ����Ϣ
			String msg=receive();
			for (serverThread serverThread : all) {
				if(serverThread==this){//���Լ��ų�����
					continue;
				}
				serverThread.send(msg);
			}
		}
		public String receive(){//���տͻ�����Ϣ
			String msg="";
			try {
				String name=count+"�ű����� ";
				msg=name+dis.readUTF();
			} catch (IOException e) {
				isRunning=false;
				CloseUtil.closeAll(dis);
				all.remove(this);
				e.printStackTrace();
			}
			return msg;
		}
		public void send(String msg){//��һ���ͻ��˷�����Ϣ
			if(msg!=null&&msg!=""){
				try {
					dos.writeUTF(msg);
				} catch (IOException e) {
					isRunning=false;
					CloseUtil.closeAll(dos);
					all.remove(this);
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
