package chatQQGroup;

/*
 * 服务器端
 * 接收每个客户端发送的信息
 * 然后把信息逐个发给除发送者以外的客户端
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	//用一个List集合来存放每个客户端对应的服务器线程
	private List<serverThread> all=new ArrayList<serverThread>();
	int count=0;//计算聊天客户数量
	public static void main(String[] args) {
		new Server().startUp();
	}
	
	@SuppressWarnings("resource")
	public void startUp(){
		try {
			//设置监听端口
			ServerSocket server=new ServerSocket(6666);
			System.out.println("***********服务器端************");
			while(true){
				//监听客户端
				Socket client=server.accept();
				count++;
				//多线程处理
				new Thread(new serverThread(client)).start();
				System.out.println("已经有"+count+"位同学进群聊天了");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class serverThread implements Runnable{
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning=true;
		//构造函数，初始化数据输入输出流
		public serverThread(Socket client){
			try {
				dis=new DataInputStream(client.getInputStream());
				dos=new DataOutputStream(client.getOutputStream());
				all.add(this);//把线程加入到集合里去
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(isRunning){
				//群发
				sentGroup();
			}
		} 
		public void sentGroup(){//群发信息
			String msg=receive();
			for (serverThread serverThread : all) {
				if(serverThread==this){//把自己排除在外
					continue;
				}
				serverThread.send(msg);
			}
		}
		public String receive(){//接收客户端信息
			String msg="";
			try {
				String name=count+"号宝宝： ";
				msg=name+dis.readUTF();
			} catch (IOException e) {
				isRunning=false;
				CloseUtil.closeAll(dis);
				all.remove(this);
				e.printStackTrace();
			}
			return msg;
		}
		public void send(String msg){//对一个客户端发送信息
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
