package com.betroc.controller;

import com.betroc.model.User;
import com.betroc.payload.ApiResponse;
import com.betroc.payload.SignUpRequest;
import com.betroc.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void registerUserTestWithNoContentSend() throws Exception {


        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)

            ).andExpect(status().isBadRequest());
    }


//    @Test
//    public void registerUserTest() throws Exception {
//
//        SignUpRequest signUpRequest = new SignUpRequest();
//
//        signUpRequest.setName("achraf");
//        signUpRequest.setEmail("smlali.achraf@gmail.com");
//        signUpRequest.setUsername("achraf");
//        signUpRequest.setPassword("achraf");
//
//        String requestBody = "{\"name\" : \"achraf333\",\"username\" : \"achraf333\",\"email\" : \"smlali.achraf@gmail.com\",\"password\" : \"achraf\"}";
//        String name = "achraf";
//        String username = "achraf";
//        String email = "smlali.achraf@gmail.com";
//        String password = "achraf";
//
//        ApiResponse response = new ApiResponse(true,"User registered successfully please confirme");
//
//        mvc.perform(post("/api/auth/signup")
//                    .param("name",name)
//                    .param("username",username)
//                    .param("email",email)
//                    .param("password",password)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(requestBody))
//                .andExpect(status().isOk());
//          //      .andExpect(jsonPath("$.success",is(true)));
//       // List<User> user = userRepository.findAll();
//        assertThat(true).isTrue();
//    }




    @Test
    public void authenticateUserTestWithAnEmmptyBody() throws Exception {
        mvc.perform(post("/api/auth/signin")).andExpect(status().isBadRequest());
    }
}
