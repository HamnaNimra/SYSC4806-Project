package Sysc4806Group.demo;

import Sysc4806Group.demo.repositories.BookRepository;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.Arrays;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class AmazinApplicationTests {

    @Test
    void contextLoads() {
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc userMvc;

    @Autowired
    private BookRepository repository;

    @Before
    void setup(){}


    @Test
    public void getSignup() throws Exception {
        MvcResult result = this.userMvc.perform(get("/signup")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert(contents.contains("First Name"));
        assert(contents.contains("Last Name"));
        assert(contents.contains("Email"));
        assert(contents.contains("Password"));
        assert(contents.contains("Admin?"));
    }

    @Test
    public void postSignup() throws Exception {

    }

    /*@Test
    public void searchWithItemsTest() throws Exception {
        String url = "http://localhost:8080" + "/uploadBook";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("isbn", "1234"),
                        new BasicNameValuePair("author", "Abhiram Santhosh"),
                        new BasicNameValuePair("title", "The inheritance of rome"),
                        new BasicNameValuePair("publisher", "Penguin books"),
                        new BasicNameValuePair("inventory", "1"),
                        new BasicNameValuePair("pictureUrl", "blank")
                ))))).andExpect(status().isOk()).andDo(

                result -> mockMvc.perform(get("/search?title=rome")).andDo(print()).andExpect(
                        content().string(containsString("1234"))
                )
        );
    }*/
}
