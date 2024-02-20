package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.business.SGRPurchase;
import com.zegline.sgrspring.model.business.SGRStore;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import com.zegline.sgrspring.repository.business.SGRPurchaseRepository;
import com.zegline.sgrspring.repository.business.SGRStoreRepository;
import com.zegline.sgrspring.repository.business.paging.SGRStorePagingSortingRepository;
import com.zegline.sgrspring.service.business.SGRItemService;
import com.zegline.sgrspring.service.business.SGRStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class SGRStoreController {

    @Autowired
    SGRItemRepository ir;

    @Autowired
    SGRStoreService ss;

    @Autowired
    SGRStoreRepository sr;

    @Autowired
    SGRStorePagingSortingRepository sgrStorePagingSortingRepository;

    @Autowired
    SGRPurchaseRepository pr;

    @PostMapping("/{storeId}/scan")
    public ResponseEntity<SGRPurchase> scanItem(
            @PathVariable String storeId,
            @RequestParam String itemId) {

        // Check for store existance
        Optional<SGRStore> optionalSGRStore = sr.findById(storeId);
        if (optionalSGRStore.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Check for item existance
        Optional<SGRItem> optionalSGRItem = ir.findById(itemId);
        if (optionalSGRItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Create and save new Purchase entity
        SGRPurchase purchase = ss.createPurchase(optionalSGRStore.get(), optionalSGRItem.get());
        pr.save(purchase);

        // Return Purchase as OK ResponseEntity
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/{id}/garantie")
    public ResponseEntity<Double> getGarantieTotalForPeriod(
            @PathVariable String id,
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        Optional<SGRStore> optStore = sr.findById(id);
        if (optStore.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Double garantieTotal = ss.getGarantieForPeriod(optStore.get(), startDate, endDate);
        return ResponseEntity.ok(garantieTotal);
    }

    @PostMapping("/nou")
    @ResponseStatus(HttpStatus.CREATED)
    public SGRStore createStore(@RequestBody Map<String, Object> requestBody) {
        String storeName = (String) requestBody.get("store_name");
        String storeDesc = (String) requestBody.get("store_desc");

        return ss.createStore(storeName, storeDesc);
    }


    @GetMapping("/toate")
    public List<SGRStore> getStoresPaginated(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return sgrStorePagingSortingRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().toList();
    }
}
