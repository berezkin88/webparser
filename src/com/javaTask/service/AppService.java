package com.javaTask.service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.javaTask.utils.Utilities;

public class AppService {
	private static Logger log = Logger.getLogger(AppService.class.getName());
	static ObjectMapper mapper = new ObjectMapper();

	public static void writeToJSON(Website web) throws IOExc {
		log.info("Started writing to JSON");
		try {
			mapper.writeValue(new File(
				System.getProperty("user.dir") + File.separator + "data" + File.separator + "website.json"), web);
		} catch (IOException e) {
			throw new IOExc("Exception is thrown in writeToJSON() method");
		}
		log.info("Finished writing to JSON");
	}
	
	public static void writeToXML(Website web) throws JAXBExc {		
		try {
			log.info("Started writing to XML");
			JAXBContext context = JAXBContext.newInstance(Website.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(web, new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "website.xml"));
			log.info("Finished writing to XML");
		} catch (JAXBException e) {
			throw new JAXBExc("Exception is thrown in writeToXML() method");
		}
	}
	
	public static Website collectData(String url) {
		log.info("Started collecting web data");
		Website website = new Website(url);
		
		try {
			Utilities.setName(website);
			Utilities.setImageUrl(website);
			Utilities.setPrice(website);
			Utilities.setSpecs(website);
			Utilities.setDesc(website);
		} catch (IOExc e) {
			e.printStackTrace();
		}
		
		log.info("Finished collecting web data");
		return website;
	}
}
