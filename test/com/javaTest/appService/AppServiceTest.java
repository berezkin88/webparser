package com.javaTest.appService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTask.entity.Website;
import com.javaTask.service.AppService;

class AppServiceTest {

	@Test
	void testCollectData() {
		Website test = null;
		test = AppService.collectData("https://prom.ua/p895411456-igrovoj-noutbu-omen.html");
		
		assertTrue(test != null);
	}

	@Test
	void testWriteToXML() {
		File file = null;
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Website.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal("Hello", file = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "webTest.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(file != null);
	}
	
	@Test
	void testWriteToJSON() {
		File file = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(file = new File(
				System.getProperty("user.dir") + File.separator + "data" + File.separator + "webTest.json"), "Hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(file != null);
	}
}
