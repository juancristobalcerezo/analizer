package org.decibel.services.webparser.service;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTagsCollection;
import org.decibel.services.webparser.model.TagsCollectionMapper;
import org.decibel.services.webparser.service.parser.IParser;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.decibel.services.webparser.service.rest.UnirestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
class ImageIdentifier implements ITagIdentifier {

	private Logger logger = LoggerFactory.getLogger(ImageIdentifier.class);
	
	@Value("${store-url}")
	private  String storeUrl;
	
	
	private UnirestFacade unirestFacade;;
	
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
	public List<ImageTagsCollection> getCollection(URI uri) throws ParserEngineException {
	
			Set<ITag> tags = parse(uri);
			
			List<ImageTagsCollection> result = mapper.toTagsCollection(uri,tags);
			
			try {
				store(result);
			} catch (JsonProcessingException e) {
				throw new ParserEngineException(e.getMessage());
			}
			
			return result;
			
		
		
	}

	private void store(List<ImageTagsCollection> collection) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		getUnirestFacade().post(storeUrl,mapper.writeValueAsString(collection));
		
	}

	public UnirestFacade getUnirestFacade() {
		
		if (unirestFacade == null) {
			unirestFacade = new UnirestFacade(); 
		}
		 
		return unirestFacade;
	}

	

	
}
