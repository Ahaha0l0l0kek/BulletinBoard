package eu.senla.alexbych.bulletinboard.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@WithMockUser
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPostById() throws Exception{
        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk()).andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"title\": \"sofa\",\n" +
                        "    \"price\": 2300.0,\n" +
                        "    \"picture\": \"sofa.png\",\n" +
                        "    \"description\": \"super sofa, very soft\",\n" +
                        "    \"postTime\": \"2020-01-01T00:00:00\",\n" +
                        "    \"priority\": false,\n" +
                        "    \"active\": true,\n" +
                        "    \"categoryId\": 1,\n" +
                        "    \"user\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"login\": \"pasha\",\n" +
                        "        \"password\": null,\n" +
                        "        \"rating\": 3.0,\n" +
                        "        \"firstname\": \"Pavel\",\n" +
                        "        \"lastname\": \"F\",\n" +
                        "        \"phoneNumber\": \"99999999999\",\n" +
                        "        \"role\": {\n" +
                        "            \"id\": 2,\n" +
                        "            \"name\": \"ROLE_USER\"\n" +
                        "        },\n" +
                        "        \"posts\": null,\n" +
                        "        \"ratings\": null\n" +
                        "    },\n" +
                        "    \"comments\": [\n" +
                        "        {\n" +
                        "            \"id\": 2,\n" +
                        "            \"userId\": 3,\n" +
                        "            \"postId\": 1,\n" +
                        "            \"commentTime\": \"2021-11-11T00:00:00\",\n" +
                        "            \"commentText\": \"its not original lol\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"userId\": 1,\n" +
                        "            \"postId\": 1,\n" +
                        "            \"commentTime\": \"2020-01-02T00:00:00\",\n" +
                        "            \"commentText\": \"bad sofa\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"));
    }
}