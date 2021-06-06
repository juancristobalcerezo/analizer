package org.decibel.services.webparser.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class TagsCollectionMapper {

	public List<ImageTagsCollection> toTagsCollection(URI uri, Set<ITag> tags) {

		Map<String, ImageTagsCollection> map = new LinkedHashMap<>();
		ITag tag = null;
		ImageTagsCollection collection = null;

		Iterator<ITag> itTags = tags.iterator();

		while (itTags.hasNext()) {
			tag = itTags.next();

			if (map.containsKey(tag.getDomain())) {

				collection = map.get(tag.getDomain());
			} else {
				collection = new ImageTagsCollection();
				collection.setDomain(tag.getDomain());

				map.put(tag.getDomain(), collection);
			}

			collection.add(tag.getUrl());
		}

		
		return new ArrayList<>(map.values());
	}

	/*
	 * public TagsCollection toTagsCollection(URI uri, Set<ITag> tags) {
	 * TagsCollection tagsCollection = new TagsCollection();
	 * 
	 * if (!tags.isEmpty()) { tagsCollection.setDomain(uri.getHost());
	 * tagsCollection.setImages(collectSources(tags)); }
	 * 
	 * return tagsCollection; }
	 */

	/*
	 * private List<String> collectSources(Set<ITag> tags) { List<String> sources =
	 * new ArrayList<>(); Iterator<ITag> itTags = tags.iterator(); while
	 * (itTags.hasNext()) { sources.add(itTags.next().getPayload()); }
	 * 
	 * return sources; }
	 */

}
