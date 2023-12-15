package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRStore;
import com.zegline.sgrspring.repository.business.SGRStoreRepository;
import com.zegline.sgrspring.service.business.SGRStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class SGRStoreController {

    @Autowired
    SGRStoreService ss;

    @Autowired
    SGRStoreRepository sr;

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

}
