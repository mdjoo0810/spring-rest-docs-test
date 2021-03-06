package kr.springrestdocstest;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.springrestdocstest.model.User;
import kr.springrestdocstest.model.UserCommand;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiDocumentation {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testRead() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user_read",
                        pathParameters(
                                parameterWithName("id").description("????????? id")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").description("????????????"),
                                fieldWithPath("data.id").description("id"),
                                fieldWithPath("data.account").description("??????"),
                                fieldWithPath("data.email").description("?????????"),
                                fieldWithPath("data.phoneNumber").description("????????????"),
                                fieldWithPath("data.createdAt").description("????????????"),
                                fieldWithPath("data.updatedAt").description("????????????")
                        )));
    }

    @Test
    @Transactional
    public void testSave() throws Exception {
        UserCommand requestUser = new UserCommand();
        requestUser.setAccount("testuser");
        requestUser.setPhoneNumber("123123");
        requestUser.setEmail("testuser@gmail.com");

        String content = objectMapper.writeValueAsString(requestUser);

        this.mockMvc.perform(post("/api/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user_create",
                        requestFields(
                                fieldWithPath("account").description("?????? (?????? ??????)"),
                                fieldWithPath("email").description("????????? (?????? ??????)"),
                                fieldWithPath("phoneNumber").description("???????????? (?????? ??????)")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").description("????????????"),
                                fieldWithPath("data.id").description("id"),
                                fieldWithPath("data.account").description("??????"),
                                fieldWithPath("data.email").description("?????????"),
                                fieldWithPath("data.phoneNumber").description("????????????"),
                                fieldWithPath("data.createdAt").description("????????????"),
                                fieldWithPath("data.updatedAt").description("????????????")
                        )));
    }

}
