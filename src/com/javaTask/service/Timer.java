package com.javaTask.service;

public class Timer {
	
	public static void waitSeconds(int seconds) {
		try {
			Thread.currentThread().sleep(1000*seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
