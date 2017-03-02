package chatQQGroup;

import java.io.*;

public class CloseUtil {
	public static void closeAll(Closeable... io){//
		/*
		 * Closeable �ǿ��Թرյ�����Դ��Ŀ��
		 * ...����˼�ǲ����Ƹ��������Թرղ�ȷ����������
		 */
		for (Closeable temp : io) {
			if(temp!=null){
				try {
					temp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
