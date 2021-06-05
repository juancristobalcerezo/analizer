package org.decibel.services.webparser.model;

import java.net.URI;
import java.net.URISyntaxException;

public class ImageTag implements ITag {

	private URI uri;

	@Override
	public String getDomain() {

		return uri.getHost();

	}

	@Override
	public String getUrl() {
		
		return uri.toString();
		
	}

	

	public void setSource(String value) throws URISyntaxException {
		this.uri = new URI(value);
	}

}
