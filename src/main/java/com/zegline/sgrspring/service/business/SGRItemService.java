package com.zegline.sgrspring.service.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SGRItemService {

    @Autowired
    private SGRItemRepository ir;

    public List<SGRItem> getItems() {
        return (List<SGRItem>) ir.findAll();
    }

    public Optional<SGRItem> getItemById(String id) {
        return ir.findById(id);
    }

    public SGRItem createItem(String name, double weight) {
        SGRItem item = new SGRItem(name);

        return ir.save(item);
    }

}
