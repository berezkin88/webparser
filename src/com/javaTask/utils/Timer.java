package com.javaTask.utils;

public class Timer {
	
	public static void waitSeconds(int seconds) {
		try {
			Thread.currentThread();
			Thread.sleep(1000*seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
