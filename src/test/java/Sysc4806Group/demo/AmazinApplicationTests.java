package Sysc4806Group.demo;

import Sysc4806Group.demo.entities.Role;
import Sysc4806Group.demo.repositories.BookRepository;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.Header;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void getSignup() throws Exception {
        MvcResult result = this.userMvc.perform(get("/signup")
                .contentType(MediaType.APPLICATION_JSON))
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
        userMvc.perform(post("/signup")
                .param("First Name", "Hamna")
                .param("Last Name", "Nimra")
                .param("Email", "test@gmail.com")
                .param("Password", "12345678")
                .with(csrf()))
                .andExpect(status().isFound())
                .andReturn();
    }

    @Test
    public void getSignIn() throws Exception {
        MvcResult result = this.userMvc.perform(get("/signin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contents = result.getResponse().getContentAsString();
        assert (contents.contains("Email"));
        assert (contents.contains("Password"));
    }

    @Test
    public void postSignIn() throws Exception {
        userMvc.perform(post("/signin")
                .param("Email", "test@gmail.com")
                .param("Password", "12345678")
                .with(csrf()))
                .andExpect(status().isFound())
                .andReturn();
    }

    @Test
    public void postUploadBook() throws Exception {
        userMvc.perform(post("/uploadBook")
                .param("Title", "XYZ Book")
                .param("ISBN", "123892")
                .param("Author", "West Author")
                .param("Publisher", "StudentsPub")
                .param("Picture URL", "google.com/images")
                .param("Inventory Num", "15")
                .with(csrf()))
                .andExpect(status().isFound())
                .andReturn();
    }
}
