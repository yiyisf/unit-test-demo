package com.paic.unittest.demo.controller;

import com.paic.unittest.demo.entity.User;
import com.paic.unittest.demo.service.XServiceClient;
import com.paic.unittest.demo.service.dto.XserviceDto;
import com.paic.unittest.demo.utils.IntegrationTest;
import com.paic.unittest.demo.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@AutoConfigureMockMvc
public class UserControllerITest {

    @Autowired
    private MockMvc userMock;

    @MockBean
    private XServiceClient serviceClient;

    @BeforeEach
    void setup() {

    }

    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setLogin("test_user");
        user.setFirstName("f_name");
        user.setLastName("l_name");
        user.setEmail("email@email.com");
        user.setCreatedBy("test_user");
        user.setActivated(false);

        XserviceDto dto = new XserviceDto();
        dto.setField("xxxxx");
        Optional<XserviceDto> optional = Optional.of(dto);
        ResponseEntity<XserviceDto> en = ResponseEntity.of(optional);
        Mockito.when(serviceClient.getDto()).thenReturn(en);

        userMock.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.login").value(user.getLogin()))
        .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(user.getLastName()))
        .andExpect(jsonPath("$.passwordHash").value(dto.getField()))
        ;
    }



}