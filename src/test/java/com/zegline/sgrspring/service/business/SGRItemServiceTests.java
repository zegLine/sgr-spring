package com.zegline.sgrspring.service.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SGRItemServiceTests {

    @Autowired
    SGRItemRepository ir;

    @Autowired
    SGRItemService is;

    @Test
    @Transactional
    public void testCreateItem() {
        // Test Data
        String name = "test item";
        double weight = 1.2;

        // Create an item and get its generated ID
        SGRItem created = is.createItem(name, weight);
        String created_id = created.getId();

        // The name should match the test data
        assertEquals(created.getItem_name(), name);
        // Find it in the repo
        Optional<SGRItem> opt_found = ir.findById(created_id);
        if (opt_found.isEmpty()) fail("SGRItem not found in Repo after created using Service");

        // Hopefully it is the same one as the one we created
        SGRItem found = opt_found.get();
        assertEquals(found, created);
    }

    @Test
    @Transactional
    public void testGetAllItems() {
        // Insert two items into the repo
        SGRItem created1 = is.createItem("created1", 0.0);
        SGRItem created2 = is.createItem("created2", 0.0);

        // Query using Service getItems function
        List<SGRItem> queried = is.getItems();

        // Hopefully, the items should be returned
        assertTrue(queried.contains(created1));
        assertTrue(queried.contains(created2));

        // The queried should ONLY contain the two elements
        // since the DB should be clear before the test
        assertEquals(queried.size(), 2);
    }

}
