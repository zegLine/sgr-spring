package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.business.SGRStore;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import com.zegline.sgrspring.service.business.SGRItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class SGRItemController {

    @Autowired
    SGRItemService sgrItemService;

    @GetMapping("/toate")
    public List<SGRItem> getAllItem() {
        return sgrItemService.getItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SGRItem> getItemById(@PathVariable String id) {
        return sgrItemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public SGRItem createItem(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        double weight = Double.parseDouble(requestBody.get("weight").toString());

        return sgrItemService.createItem(name, weight);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SGRItem> deleteItem(@PathVariable String itemId) {
        Optional<SGRItem> itemToDelete = sgrItemService.deleteItem(itemId);

        if (itemToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(itemToDelete.get(), HttpStatus.OK);
    }

}
