package org.decibel.services.webparser.model;

import java.net.URI;
import java.net.URISyntaxException;

public class ImageTag implements ITag {

	private URI uri;

	

	@Override
	public String getPayload() {
		
		return uri.toString();
		
	}

	

	public void setSource(String value) throws URISyntaxException {
		this.uri = new URI(value);
	}



	public String getSrc() {
		return getPayload();
	}

}
