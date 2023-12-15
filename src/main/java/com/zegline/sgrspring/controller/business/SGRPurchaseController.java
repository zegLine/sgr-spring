package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRPretGarantie;
import com.zegline.sgrspring.model.business.SGRPurchase;
import com.zegline.sgrspring.service.business.SGRPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/purchase")
public class SGRPurchaseController {

    @Autowired
    SGRPurchaseService ps;

    @GetMapping("/{id}/garantie")
    public ResponseEntity<SGRPretGarantie> getGarantieObjForPurchase(@PathVariable String id){
        Optional<SGRPurchase> optPurchase = ps.getPurchaseById(id);

        if (optPurchase.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "couldn't find purchase with given id");
        }

        SGRPretGarantie returnedPg = ps.getGarantieObjForPurchase(optPurchase.get());

        return ResponseEntity.ok(returnedPg);
    }

}
