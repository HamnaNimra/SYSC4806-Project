package Sysc4806Group.demo;

import Sysc4806Group.demo.controllers.BookController;
import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class AmazinApplicationTests {


	@Test
	void contextLoads() {
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository repository;

	@Test
	public void blankSearch() throws Exception{
		this.mockMvc.perform(get("/search?title=Summer")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void searchWithItemsTest() throws Exception{
		String url = "http://localhost:8080" + "/uploadBook";

		mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
						new BasicNameValuePair("isbn","1234"),
						new BasicNameValuePair("author","Abhiram Santhosh"),
						new BasicNameValuePair("title", "The inheritance of rome"),
						new BasicNameValuePair("publisher","Penguin books"),
						new BasicNameValuePair("inventory","1"),
						new BasicNameValuePair("pictureUrl", "blank")
				))))).andExpect(status().isOk()).andDo(

						result -> mockMvc.perform(get("/search?title=rome")).andDo(print()).andExpect(
								content().string(containsString("1234"))
						)

		);



	}




}


