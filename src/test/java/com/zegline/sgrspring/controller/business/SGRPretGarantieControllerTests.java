package com.zegline.sgrspring.controller.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zegline.sgrspring.model.business.SGRPretGarantie;
import com.zegline.sgrspring.repository.business.SGRPretGarantieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SGRPretGarantieControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SGRPretGarantieRepository pgr;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetLatestPretGarantie() throws Exception {

        SGRPretGarantie newest = new SGRPretGarantie();
        newest.setPrice(0.398);
        pgr.save(newest);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/garantie/pret/curent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(0.398));

    }

    @Test
    public void testGetAllPretGarantieItems() throws Exception {
        // Create some SGRPretGarantie items and save them
        SGRPretGarantie item1 = new SGRPretGarantie();
        item1.setPrice(0.398);
        pgr.save(item1);

        SGRPretGarantie item2 = new SGRPretGarantie();
        item2.setPrice(0.499);
        pgr.save(item2);

        // Perform a GET request to retrieve all items
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/garantie/pret/toate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Deserialize the JSON response into a list of SGRPretGarantie items
        String responseContent = result.getResponse().getContentAsString();
        List<SGRPretGarantie> items = objectMapper.readValue(responseContent, new TypeReference<List<SGRPretGarantie>>() {});

        // Assert that the response contains the expected number of items
        Assertions.assertEquals(2, items.size());

        // You can add more assertions as needed to verify the content of the items.
    }

}
