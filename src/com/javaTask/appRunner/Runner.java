package com.javaTask.appRunner;

import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.javaTask.service.AppService;

public class Runner {
	
	public static void main(String[] args) {
		Website web = AppService.collectData("https://prom.ua/p895411456-igrovoj-noutbu-omen.html");

		try {
			AppService.writeToJSON(web);
			AppService.writeToXML(web);
		} catch (IOExc | JAXBExc e) {
			e.printStackTrace();
		}

	}
}
