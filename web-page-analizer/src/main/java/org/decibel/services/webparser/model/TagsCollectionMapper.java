package org.decibel.services.webparser.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class TagsCollectionMapper {

	public TagsCollection toTagsCollection(URI uri, Set<ITag> tags) {
		TagsCollection tagsCollection = new TagsCollection();
		
		if (!tags.isEmpty()) {
			tagsCollection.setDomain(uri.getHost());
			tagsCollection.setImages(collectSources(tags));
		}
		
		return tagsCollection;
	}

	private List<String> collectSources(Set<ITag> tags) {
		List<String> sources = new ArrayList<>();
		Iterator<ITag> itTags = tags.iterator();
		while(itTags.hasNext()) {
			sources.add(itTags.next().getUrl());
		}
		
		return sources;
	}

}
