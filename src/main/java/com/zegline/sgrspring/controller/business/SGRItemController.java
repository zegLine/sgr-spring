package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.business.SGRStore;
import com.zegline.sgrspring.model.filter.SGRFilterSelected;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import com.zegline.sgrspring.service.business.SGRItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @PostMapping("/toate")
    public ResponseEntity<Page<SGRItem>> getAllItem(
            @RequestParam int pageSize,
            @RequestParam int pageNumber,
            @RequestParam(required = false) String sortingColumn,
            @RequestParam(required = false) String sortingDirection,
            @RequestBody(required = false) Map<String, List<SGRFilterSelected>> body) {

        if (sortingColumn == null) {
            return new ResponseEntity<>(sgrItemService.getItemsPaginated(pageSize, pageNumber), HttpStatus.OK);
        }

        Optional<Sort.Direction> directionOptional = Sort.Direction.fromOptionalString(sortingDirection);

        if (directionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(sgrItemService.getItemsPaginatedSorted(pageSize, pageNumber, sortingColumn, directionOptional.get()), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SGRItem> getItemById(@PathVariable String id) {
        return sgrItemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/nou")
    @ResponseStatus(HttpStatus.CREATED)
    public SGRItem createItem(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        double weight = Double.parseDouble(requestBody.get("weight").toString());

        return sgrItemService.createItem(name, weight);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SGRItem> deleteItem(@PathVariable String id) {
        Optional<SGRItem> itemToDelete = sgrItemService.deleteItem(id);

        if (itemToDelete.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(itemToDelete.get(), HttpStatus.OK);
    }

}
