package org.decibel.services.webparser.service.parser.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTag;
import org.decibel.services.webparser.service.parser.IParser;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JSoupWrapper implements IParser {
	private Logger logger = LoggerFactory.getLogger(JSoupWrapper.class);

	@Override
	public Set<ITag> extract(File file, String tagName) throws ParserEngineException {
		try {
			return buildTags(getDocument(file), tagName);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ParserEngineException(e.getMessage());
		}
	}

	@Override
	public Set<ITag> extract(String resource, String tagName) throws ParserEngineException {

		try {
			return buildTags(getDocument(resource), tagName);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ParserEngineException(e.getMessage());
		}

	}

	private Set<ITag> buildTags(Document doc, String tagName) throws ParserEngineException {
		try {

			Set<ITag> result = new LinkedHashSet<>();
			ITag tagInstance = null;

			Elements newsHeadlines = doc.select(tagName);
			for (Element headline : newsHeadlines) {

				tagInstance = instanceTargetTag(headline, tagName);
				if (tagInstance != null) {
					result.add(tagInstance);
				}

			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ParserEngineException(e.getMessage());
		}
	}

	private ITag instanceTargetTag(Element headline, String tagName) throws URISyntaxException {

		String src = null;
		if (IParser.IMG.equals(tagName)) {

			ImageTag imageTag = new ImageTag();

			src = getImageSrc(headline);

			if (src == null) {
				logger.warn("Tag has no src: " + headline.html());
			} else {
				imageTag.setSource(src);
			}
			return imageTag;
		}

		return null;
	}

	private String getImageSrc(Element headline) {

		String[] attributes = { "src", "data-src" };

		String src = null;

		for (int i = 0; i < attributes.length && src == null; i++) {
			src = headline.attr(attributes[i]);
		}

		return src;

	}

	private Document getDocument(File file) throws IOException {
		return Jsoup.parse(file, "UTF-8");
	}

	private Document getDocument(String resource) throws IOException {
		return Jsoup.connect(resource).get();
	}

}
