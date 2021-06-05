package org.decibel.services.webparser.service.parser;

import java.io.File;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;

public interface IParser {

	String IMG = "img";

	Set<ITag> extract(String url,String tag) throws ParserEngineException;

	Set<ITag> extract(File file, String tag) throws ParserEngineException;


}