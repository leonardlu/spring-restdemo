package restdemo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static restdemo.utils.AssertUtils.assertThatRestObject;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import restdemo.controller.OctusRestController;
import restdemo.objects.RestObject;
import restdemo.service.IDataService;


@RunWith(MockitoJUnitRunner.class)
public class OctusRestControllerTest {
	@Mock
	private IDataService service;

	private MockMvc mockMvc;

	private static String CONTENTS;
	
	@Before
	public void setUp() {
		// using http://jettison.codehaus.org/
		JSONObject outerObject = new JSONObject();
		JSONArray outerArray = new JSONArray();
		JSONObject innerObject = new JSONObject();
		JSONArray innerArray = new JSONArray();

		innerArray.put("test");
		innerArray.put("test1");
		innerArray.put("7676");

		innerObject.put("test2", "2");
		innerObject.put("data", innerArray);
		outerArray.put(innerObject);
		outerObject.put("rows", outerArray);
		
		CONTENTS = outerObject.toString();
		
		mockMvc = MockMvcBuilders.standaloneSetup(new OctusRestController(service)).build();
	}

	@Test
	public void getAllObjects() throws Exception {
		mockMvc.perform(get("/api/"))
		.andExpect(status().isOk());

	}

	@Test
	public void postObject() throws Exception {
		mockMvc.perform(post("/api/")
				.contentType(MediaType.TEXT_PLAIN)
				.content(CONTENTS)
				);
		ArgumentCaptor<RestObject> createdArgument = ArgumentCaptor.forClass(RestObject.class);

		verify(service, times(1)).create(createdArgument.capture());
		verifyNoMoreInteractions(service);

		RestObject created = createdArgument.getValue();
		assertThatRestObject(created)
		.hasContents(CONTENTS);
	}

	@Test
	public void deleteObject() throws Exception{
		mockMvc.perform(delete("/api/{id}", "id1")
				);
		ArgumentCaptor<String> deleteArgument = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).delete(deleteArgument.capture());
		String id = deleteArgument.getValue();
		assertEquals(id,"id1");
	}

	@Test
	public void putObject() throws Exception{
		mockMvc.perform(put("/api/{id}", "id1")
				.contentType(MediaType.TEXT_PLAIN_VALUE)
				.content(CONTENTS)
				);
		ArgumentCaptor<String> updatedArgument1 = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> updatedArgument2 = ArgumentCaptor.forClass(String.class);
        verify(service, times(1)).update(updatedArgument1.capture(),updatedArgument2.capture());
		String contents = updatedArgument2.getValue();
		String id = updatedArgument1.getValue();
		assertEquals(CONTENTS, contents);
		assertEquals("id1", id);
	}

}
