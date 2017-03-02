package chatQQGroup;

import java.io.*;

public class CloseUtil {
	public static void closeAll(Closeable... io){//
		/*
		 * Closeable 是可以关闭的数据源或目标
		 * ...的意思是不限制个数，可以关闭不确定个数的流
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
