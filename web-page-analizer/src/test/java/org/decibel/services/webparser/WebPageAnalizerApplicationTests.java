package org.decibel.services.webparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.decibel.services.webparser.model.ITag;
import org.decibel.services.webparser.model.ImageTag;
import org.decibel.services.webparser.model.TagsCollection;
import org.decibel.services.webparser.service.ITagIdentifier;
import org.decibel.services.webparser.service.parser.ParserEngineException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WebPageAnalizerApplicationTests {

	private String htmlFile = "src/test/resources/view-source_https___stackoverflow.com.html";

	@Autowired
	private ITagIdentifier imageIdentifier;

	
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testParserSurfsTheNet() {

		Set<ITag> tags = null;

		try {

			tags = imageIdentifier.parse(new URI("http://www.google.com"));

		} catch (ParserEngineException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(tags);

	}

	@Test
	public void testToCollectImages() {

		File file = new File(htmlFile);

		Set<ITag> tags = null;

		try {

			tags = imageIdentifier.parse(file);

		} catch (ParserEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(46, tags.size());

	}

	@Test
	public void testToParseImagesDomainAndUrl() {

		File file = new File(htmlFile);

		Set<ITag> tags = null;
		try {

			tags = imageIdentifier.parse(file);

		} catch (ParserEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object[] tagsArray = tags.toArray();

		ITag first = (ITag) tagsArray[0];
		ITag last = (ITag) tagsArray[tagsArray.length - 1];

		// It just an exercise

		
		assertEquals("https://cdn.sstatic.net/Img/teams/teams-illo-free-sidebar-promo.svg?v=47faa659a05e",
				first.getPayload());

		
		assertEquals("https://pixel.quantserve.com/pixel/p-c1rF4kxgLUzNc.gif", last.getPayload());

	}

	@Test
	public void testToCollectDomainAndUrl() {
		ImageTag tag = new ImageTag();
		try {
			tag.setSource("https://stackoverflow.com/questions/9607903/get-domain-name-from-given-url");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals("https://stackoverflow.com/questions/9607903/get-domain-name-from-given-url", tag.getSrc());
	

	}

	@Test
	public void testServiceReturnsAJson() {
		
		TagsCollection tagsCollection = null;
		String resultString = null;
		try {
			ResultActions result = mockMvc.perform(post("/collect-images").content(
					"https://github.com/juancristobalcerezo/decibel"))
					.andExpect(status().isOk());
			
			resultString = result.andReturn().getResponse().getContentAsString();
			
			ObjectMapper objectMapper = new ObjectMapper();
			tagsCollection = objectMapper.readValue(resultString, TagsCollection.class);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(resultString);
		assertEquals("github.com",tagsCollection.getDomain());
		assertEquals("{\"domain\":\"github.com\",\"images\":[\"https://github.githubassets.com/images/search-key-slash.svg\",\"https://avatars.githubusercontent.com/u/31847993?s=48&v=4\"]}",resultString);
		
		for (String tag:tagsCollection.getImages()) {
			System.out.println(tag);
		}
	}
	
	

}
