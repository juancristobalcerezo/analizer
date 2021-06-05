package org.decibel.services.webparser.service;

import java.io.File;
import java.net.URI;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.TagsCollection;
import org.decibel.services.webparser.service.parser.ParserEngineException;


public interface ITagIdentifier {

	Set<ITag> parse(URI uri) throws ParserEngineException;
	Set<ITag> parse(File file) throws ParserEngineException;
	
	TagsCollection getCollection(URI uri) throws ParserEngineException;

	

}
