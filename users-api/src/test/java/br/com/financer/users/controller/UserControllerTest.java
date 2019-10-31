package br.com.financer.users.controller;

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.financer.users.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private static String authorization;
	private static final String USER = "admin";
	private static final String PASSWORD = "123456";
	private static final String RESOURCE = "/users";
	private static final HttpHeaders HEADERS = new HttpHeaders();
	private static final LocalDateTime now = LocalDateTime.now();
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	private String json(Object o) throws IOException {
		try {
			MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
			this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
			return mockHttpOutputMessage.getBodyAsString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@BeforeClass
	public static void setUp() throws Exception {

		String auth = USER + ":" + PASSWORD;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
		authorization = "Basic " + new String(encodedAuth);
		
		HEADERS.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HEADERS.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

	}
	
	@Test
	public void listAllTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE)
			.headers(HEADERS))
			.andExpect(status().isOk());
	}
	
	@Test
	public void createTest() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
							.post(RESOURCE)
							.content(json(new User(null, "Jhon Doe", true, "jhon@email.com", "123456", now, new ArrayList<>())))
							.headers(HEADERS); 
				
		mvc.perform(requestBuilder)
			.andExpect(status().isCreated());
		
	}
	
	@Test
	public void fetchTest() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
							.post(RESOURCE)
							.content(json(new User(null, "Charles Grey", true, "charles@email.com", "123456", now, new ArrayList<>())))
							.headers(HEADERS); 
				
		MvcResult response = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders
				.get(RESOURCE + "/" + response.getResponse().getHeader("Location").split("users/")[1])
				.headers(HEADERS))
				.andExpect(status().isOk())
				.andExpect(content().string(notNullValue()));
		
	}
	
	@Test
	public void updateTest() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
							.post(RESOURCE)
							.content(json(new User(null, "Robert Grant", true, "robert@email.com", "123456", now, new ArrayList<>())))
							.headers(HEADERS); 
				
		MvcResult response = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders
				.put(RESOURCE + "/" + response.getResponse().getHeader("Location").split("users/")[1])
				.content(json(new User(null, "Robert Granted", true, "robert@email.com", "123456", now, new ArrayList<>())))
				.headers(HEADERS))
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders
				.put(RESOURCE + "/0000000")
				.content(json(new User(null, "Robert Granted", true, "robert@email.com", "123456", now, new ArrayList<>())))
				.headers(HEADERS))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void deleteTest() throws Exception {
		
		RequestBuilder requestBuilder = 
				MockMvcRequestBuilders
							.post(RESOURCE)
							.content(json(new User(null, "Joe Fletcher", true, "joe@email.com", "123456", now, new ArrayList<>())))
							.headers(HEADERS); 
				
		MvcResult response = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders
				.delete(RESOURCE + "/" + response.getResponse().getHeader("Location").split("users/")[1])
				.headers(HEADERS))
				.andExpect(status().isNoContent());
		
		mvc.perform(MockMvcRequestBuilders
				.delete(RESOURCE + "/0000000")
				.headers(HEADERS))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void notFoundTest() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get(RESOURCE + "/0000000")
			.headers(HEADERS))
			.andExpect(status().isNotFound())
			.andExpect(content().string("User not found!"));
		
	}

}
