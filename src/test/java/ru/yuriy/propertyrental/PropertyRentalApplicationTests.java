package ru.yuriy.propertyrental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PropertyRentalApplicationTests
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {}

    @Test
    @DisplayName("Test login")
    public void loginTest() throws Exception
    {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test registration")
    public void registrationTest() throws Exception
    {
        String noAuth = "false";

        mockMvc.perform(get("/registration")
                        .param("noAuth", noAuth))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test user profile with Basic Authentication")
    public void userProfileTestWithBasicAuth() throws Exception
    {
        String id = "1";
        String username = "";
        String password = "";

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        mockMvc.perform(get("/profile/{id}", id)
                        .header("Authorization", basicAuth))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}