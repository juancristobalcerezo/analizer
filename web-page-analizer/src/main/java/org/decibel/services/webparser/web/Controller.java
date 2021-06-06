package org.decibel.services.webparser.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.decibel.services.webparser.model.ImageTagsCollection;
import org.decibel.services.webparser.service.ITagIdentifier;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class Controller {
	
	private Logger logger = LoggerFactory.getLogger(Controller.class);
	
	
	@Autowired
	private ITagIdentifier imageIdentifier;
	
	
	
	@PostMapping("/collect-images")
	public ResponseEntity<List<ImageTagsCollection>> collectImages(@RequestParam(name = "url")  String url, HttpServletRequest request) {
		
		
		try {
			return new ResponseEntity<>(imageIdentifier.getCollection(new URI(url)), HttpStatus.OK);
		} catch (ParserEngineException | URISyntaxException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
