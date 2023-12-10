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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class SGRItemServiceTests {

    @Autowired
    SGRItemRepository ir;

    @Autowired
    SGRItemService is;

    @Test
    @Transactional
    public void testCreateItem() {
        String name = "test item";
        double weight = 1.2;

        SGRItem created = is.createItem(name, weight);
        String created_id = created.getId();

        // Initialize the purchases collection
        created.getPurchases();

        assertEquals(created.getItem_name(), name);
        Optional<SGRItem> opt_found = ir.findById(created_id);
        if (opt_found.isEmpty()) fail("SGRItem not found in Repo after created using Service");

        SGRItem found = opt_found.get();
        assertEquals(found, created);
    }

}
