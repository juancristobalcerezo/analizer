package org.decibel.services.webparser.service;

import java.io.File;
import java.net.URI;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.TagsCollection;
import org.decibel.services.webparser.model.TagsCollectionMapper;
import org.decibel.services.webparser.service.parser.IParser;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component
class ImageIdentifier implements ITagIdentifier {

	private Logger logger = LoggerFactory.getLogger(ImageIdentifier.class);
	
	@Autowired
	@Qualifier("JSoupWrapper")
	private IParser parser;
	
	@Autowired
	private TagsCollectionMapper mapper;
	
	@Override
	public Set<ITag> parse(URI uri) throws ParserEngineException {
		
		logger.info("Parsing "+uri.toString());
		
		return parser.extract(uri.toString(),"img");
	}

	@Override
	public Set<ITag> parse(File file) throws ParserEngineException {
		logger.info("Parsing "+file.getAbsolutePath().toString());
		
		return parser.extract(file,"img");
	}
	@Override
	public TagsCollection getCollection(URI uri) throws ParserEngineException {
	
			Set<ITag> tags = parse(uri);
			
			return mapper.toTagsCollection(uri,tags);
			
		
		
	}

	

	
}
