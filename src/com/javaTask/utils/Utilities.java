package com.javaTask.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;

public class Utilities {
	private static Document doc;
	
	public static void setMainInfo(Website web) throws IOExc {
		try {
			doc = Jsoup.connect(web.getUrl()).get();
			
		} catch (IOException e) {
			throw new IOExc("Exception thrown in setMainInfo() method");
		}
		
		Elements info = doc.select(".x-product-page__main-info");
		web.setName(info.select(".x-title").first().html());
		web.setPrice(Double.parseDouble(info.select(".x-product-price__value").first().attr("data-qaprice")));
		
		//Sequence of images
		String images = info.select("[data-bazooka-props]").attr("data-bazooka-props");
		
		//getting necessary image among all
		web.setImageUrl(images.substring(images.indexOf("image_url_640x640\": \"") + "image_url_640x640\": \"".length(), images.indexOf("\", \"id")));
	}
	
	public static void setSpecs(Website web) throws IOExc {
		Map<String, String> transSpecs = new HashMap<>();
		
		try {
			doc = Jsoup.connect(web.getUrl()).get();
		} catch (IOException e) {
			throw new IOExc("Exception thrown in setSpecs() method");
		}

		Elements specs = doc.select(".x-product-attr");
		Elements keys = specs.select("[data-qaid=\"attribute_line\"] > .x-attributes__left > .x-attributes__value");
		Elements values = specs.select("[data-qaid=\"attribute_line\"] > .x-attributes__right > .x-attributes__value");
		
		for(int i = 0, size = keys.size(); i < size; i++) {
			transSpecs.put(keys.get(i).text(), values.get(i).text());
		}
		
		web.setSpecs(transSpecs);
	}
	
	public static void setDesc(Website web) throws IOExc {
		try {
			doc = Jsoup.connect(web.getUrl()).get();
		} catch (IOException e) {
			throw new IOExc("Exception thrown in setDesc() method");
		}
		
		web.setDesc(doc.select(".x-user-content").text());
	}
}
