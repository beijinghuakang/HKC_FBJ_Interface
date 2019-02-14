package com.hkc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author csc
 * @create 2019-02-13 8:44
 */
public class OutMsg {

	private static RandomAccessFile mm;
	private static String logTitle = "0000000000";
	private final static Object objectLock = new Object();

	public static void println(String x) {
		synchronized (objectLock) {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss ");
			String dateString = formatter.format(currentTime);
			//保证一条日志一行
			String string = "\r\n" + dateString + x + "\n";
			if (mm == null || !dateString.substring(0, 10).equals(logTitle)) {
				try {
					mm.close();
				} catch (Exception e1) {
				}
				try {
					logTitle = dateString.substring(0, 10);
					mm = new RandomAccessFile("D:\\log\\" + logTitle
							+ "_lc_log.txt", "rw");
				} catch (FileNotFoundException e) {			
					e.printStackTrace();
				}
			}

			try {
				mm.seek(mm.length());
				mm.write(string.getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void println(Exception ex) {
		synchronized (objectLock) {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss ");
			String dateString = formatter.format(currentTime);

			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));

			String string = "\r\n" + dateString + "FERROR " + sw.toString();
			System.out.println(string);

			// 加入判断晚8点到早5点之间不在进行日志输出
			/*
			 * SimpleDateFormat formatter2 = new SimpleDateFormat("HHmm"); int
			 * hm = Integer.parseInt(formatter2.format(currentTime)); if (hm >
			 * 2000 || hm < 500) { return; }
			 */

			if (mm == null || !dateString.substring(0, 10).equals(logTitle)) {
				try {
					mm.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}
				try {
					logTitle = dateString.substring(0, 10);
					mm = new RandomAccessFile("D:\\log\\" + logTitle
							+ "_Ex_log.txt", "rw");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				mm.seek(mm.length());
				mm.write(string.getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public synchronized static void println(int x) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss ");
		String dateString = formatter.format(currentTime);
		System.out.println(dateString + x);
	}

	public synchronized static void println(float x) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss ");
		String dateString = formatter.format(currentTime);
		System.out.println(dateString + x);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}