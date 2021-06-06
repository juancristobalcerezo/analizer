package org.decibel.services.webparser.service;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTagsCollection;
import org.decibel.services.webparser.service.parser.ParserEngineException;


public interface ITagIdentifier {

	Set<ITag> parse(URI uri) throws ParserEngineException;
	Set<ITag> parse(File file) throws ParserEngineException;
	
	List<ImageTagsCollection> getCollection(URI uri) throws ParserEngineException;

	

}
