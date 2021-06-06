package org.decibel.services.webparser.model;

import java.util.ArrayList;
import java.util.List;

public class ImageTagsCollection {

	private String domain;
	
	private List<String> images;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<String> getImages() {
		if (images == null) {
			images = new ArrayList<>();
		}
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public void add(String url) {
		getImages().add(url);
	}
	
	
}
