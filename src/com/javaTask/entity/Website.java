package com.javaTask.entity;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Website")
public class Website {
	private String url;
	private String name;
	private double price;
	private String imageUrl;
	private Map<String, String> specs;
	private String desc;
	
	public Website(String url) {
		this.url = url;
	}
	
	public Website() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Map<String, String> getSpecs() {
		return specs;
	}

	public void setSpecs(Map<String, String> specs) {
		this.specs = specs;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
