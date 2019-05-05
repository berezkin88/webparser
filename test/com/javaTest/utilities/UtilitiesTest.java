package com.javaTest.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UtilitiesTest {
	private static Document doc;
	private static Logger log = Logger.getLogger(UtilitiesTest.class.getName());
	
	@BeforeAll
    public static void init() {
		log.info("in init() block");
		try {
			doc = Jsoup.connect("https://prom.ua/p895411456-igrovoj-noutbu-omen.html").get();
		} catch (IOException e) {
			e.printStackTrace();;
		}
    }

	@Test
	void testParseUrl() {
		assertTrue(doc != null);
	}

	
	@Test
	void testGetImagesString() {
		String imgs = null;
		
		imgs = doc.getElementsByAttribute("data-bazooka-props").attr("data-bazooka-props");
		
		assertTrue(imgs != null);
	}
	
	@Test
	void testGetSpecs() {
		Elements keys = null;
		Elements values = null;
		
		keys = doc.select("[data-qaid=\"attribute_line\"] > .x-attributes__left > .x-attributes__value");
		values = doc.select("[data-qaid=\"attribute_line\"] > .x-attributes__right > .x-attributes__value");
		
		assertTrue(keys != null);
		assertTrue(values != null);
	}
	
	@Test
	void testGetDesc() {
		String desc = null;
		
		desc = doc.getElementsByAttributeValue("data-qaid", "product_description").text();
		
		assertTrue(desc != null);
	}
	
	@Test
	void testGetName() {
		String name = null;
		
		name = doc.getElementsByAttributeValue("data-qaid", "product_name").text();
		
		assertTrue(name != null);
	}
	
	@Test
	void testGetPrice() {
		Double price = null;
		
		price = Double.parseDouble(doc.getElementsByAttribute("data-qaprice").attr("data-qaprice"));
		
		assertTrue(price != null);
	}
}
