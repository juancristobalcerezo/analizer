package org.decibel.services.webparser.service.parser.impl;

import java.net.URISyntaxException;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTag;
import org.decibel.services.webparser.service.parser.IParser;
import org.htmlparser.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractParser {

	private Logger logger = LoggerFactory.getLogger(AbstractParser.class);
public ITag instanceTargetTag(Tag tag, String tagName) throws URISyntaxException {
		
	
		String src = null;
		if (IParser.IMG.equals(tagName)) {
	
			ImageTag imageTag = new ImageTag();
			
			src = getImageSrc(tag);
			
			if (src==null) {
				logger.warn("Tag has no src: "+tag.toPlainTextString());
			}else {
				imageTag.setSource(src);
			}
			return imageTag;
		}
		
		return null;
	}

	
	public String getImageSrc(Tag tag) {
		
		String [] attributes = {"src","data-src"};
		
		String src = null;
		
		for (int i=0;i<attributes.length && src == null ;i++) {
			src = tag.getAttribute(attributes[i]);
		}
		
		
		return src;
		
	}

	
}
