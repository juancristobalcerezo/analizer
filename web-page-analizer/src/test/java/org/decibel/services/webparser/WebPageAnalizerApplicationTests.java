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
import org.decibel.services.webparser.model.ImageTagsCollection;
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

		assertEquals("cdn.sstatic.net",
				first.getDomain());
		assertEquals("https://cdn.sstatic.net/Img/teams/teams-illo-free-sidebar-promo.svg?v=47faa659a05e",
				first.getUrl());

		
		
		assertEquals("pixel.quantserve.com",
				last.getDomain());
		assertEquals("https://pixel.quantserve.com/pixel/p-c1rF4kxgLUzNc.gif", last.getUrl());

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

		assertEquals("https://stackoverflow.com/questions/9607903/get-domain-name-from-given-url", tag.getUrl());
	

	}

	@Test
	public void testServiceReturnsAJson() {
		
		ImageTagsCollection[] tagsCollection = null;
		String resultString = null;
		try {
			ResultActions result = mockMvc.perform(post("/collect-images").param("url", "https://artofcode.wordpress.com/2017/10/19/spring-boot-configuration-for-tomcats-pooling-data-source/"))
					.andExpect(status().isOk());
			
			resultString = result.andReturn().getResponse().getContentAsString();
			
			ObjectMapper objectMapper = new ObjectMapper();
			tagsCollection = objectMapper.readValue(resultString, ImageTagsCollection[].class);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals("[{\"domain\":\"artofcode.files.wordpress.com\",\"images\":[\"https://artofcode.files.wordpress.com/2013/05/cropped-header_102.jpg\",\"https://artofcode.files.wordpress.com/2017/10/2017-10-19-00_44_06-spring-boot-reference-guide_old.jpg?w=600\",\"https://artofcode.files.wordpress.com/2017/10/2017-10-19-00_45_03-spring-boot-reference-guide_new.jpg?w=600\"]},{\"domain\":\"0.gravatar.com\",\"images\":[\"https://0.gravatar.com/avatar/9052c887f0a62c26b6daf370192c595e?s=60&d=identicon&r=G\",\"https://0.gravatar.com/avatar/9052c887f0a62c26b6daf370192c595e?s=40&d=identicon&r=G\",\"https://0.gravatar.com/avatar/6eda7215e57a8a50069f8f9a56f3d67e?s=48&d=identicon&r=G\"]},{\"domain\":\"2.gravatar.com\",\"images\":[\"https://2.gravatar.com/avatar/249ac4ab2568059b2ad5c3b11bf4a685?s=40&d=identicon&r=G\",\"https://2.gravatar.com/avatar/50fc6eef24bb7e5e75ab8c65bf0da326?s=48&d=identicon&r=G\",\"https://2.gravatar.com/avatar/8c863cce44490966bad583a4477ad4f2?s=48&d=identicon&r=G\"]},{\"domain\":\"i1.wp.com\",\"images\":[\"https://i1.wp.com/pbs.twimg.com/profile_images/864858986633363456/NPNEgVMu_normal.jpg?resize=40%2C40\"]},{\"domain\":\"1.gravatar.com\",\"images\":[\"https://1.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=25&d=identicon&forcedefault=y&r=G\",\"https://1.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=25&d=identicon&forcedefault=y&r=G\",\"https://1.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=25&d=identicon&forcedefault=y&r=G\",\"https://1.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=25&d=identicon&forcedefault=y&r=G\",\"https://1.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=25&d=identicon&forcedefault=y&r=G\"]},{\"domain\":\"lh3.googleusercontent.com\",\"images\":[\"https://lh3.googleusercontent.com/a-/AOh14GgL7TSffVDi8KjpPC8eyNG52HhWU2RJsUqvaebm=s96-c\"]},{\"domain\":\"pixel.wp.com\",\"images\":[\"https://pixel.wp.com/b.gif?v=noscript\"]}]",resultString);
		
		
		assertEquals(7,tagsCollection.length);
		
		assertEquals("artofcode.files.wordpress.com",tagsCollection[0].getDomain());
		assertEquals("https://artofcode.files.wordpress.com/2013/05/cropped-header_102.jpg",tagsCollection[0].getImages().get(0));
		
		
		
		assertEquals("pixel.wp.com",tagsCollection[6].getDomain());
		assertEquals("https://pixel.wp.com/b.gif?v=noscript",tagsCollection[6].getImages().get(0));
		
		
		
		
	}
	
	

}
