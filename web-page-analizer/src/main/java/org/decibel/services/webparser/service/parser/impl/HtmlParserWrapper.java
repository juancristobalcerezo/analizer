package org.decibel.services.webparser.service.parser.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTag;
import org.decibel.services.webparser.service.parser.IParser;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Deprecated
@Component
class HtmlParserWrapper  implements IParser {

	private Logger logger = LoggerFactory.getLogger(HtmlParserWrapper.class);
	
	public Set<ITag> extract(String resource,String tagName) throws ParserEngineException {

		Set<ITag> result = new LinkedHashSet<>();
		ITag tagInstance = null;
		try {
			Parser parser = new Parser(resource);
			
			NodeList list = parser.parse(new TagNameFilter(tagName));
			
			for (SimpleNodeIterator iterator = list.elements(); iterator.hasMoreNodes();) {
			
				Tag tag = (Tag) iterator.nextNode();
				
				tagInstance = instanceTargetTag(tag,tagName);
				
				if (tagInstance != null) {
					result.add(tagInstance);
				}
				
			}
			
			return result;
			
		} catch (ParserException | URISyntaxException e) {
			logger.error(e.getMessage());
			throw new ParserEngineException(e.getMessage());
		}

	}

	
	private ITag instanceTargetTag(Tag tag, String tagName) throws URISyntaxException {
		
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

	
	private String getImageSrc(Tag tag) {
		
		String [] attributes = {"src","data-src"};
		
		String src = null;
		
		for (int i=0;i<attributes.length && src == null ;i++) {
			src = tag.getAttribute(attributes[i]);
		}
		
		
		return src;
		
	}


	@Override
	public Set<ITag> extract(File file, String tag) throws ParserEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
