package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.service.business.SGRItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SGRItemControllerTests {

    @Autowired
    SGRItemService is;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void queryControllerById() throws Exception {
        // Create item using service and get its id
        SGRItem created = is.createItem("created1", 0.0);
        String created_id = created.getId();

        // Do GET request and assert values
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/item/" + created_id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(created_id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item_weight_kg").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item_name").value("created1"));
    }

}
