package com.zegline.sgrspring.controller.business;

import com.zegline.sgrspring.model.business.SGRPretGarantie;
import com.zegline.sgrspring.repository.business.SGRPretGarantieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garantie/pret")
public class SGRPretGarantieController {

    @Autowired
    SGRPretGarantieRepository pgr;

    @PostMapping("/nou")
    public SGRPretGarantie createPretGarantie(@RequestParam double price) {
        // Create an SGRPretGarantie instance with the provided price
        SGRPretGarantie pretGarantie = new SGRPretGarantie();
        pretGarantie.setPrice(price);

        pgr.save(pretGarantie);

        return pretGarantie;
    }


    @GetMapping("/toate")
    public List<SGRPretGarantie> getAllPretGarantieItems() {
        // Retrieve all "pret garantie" items from the repository
        return (List<SGRPretGarantie>) pgr.findAll();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Long> deletePretGarantie(@PathVariable Long id) {
        Optional<SGRPretGarantie> pretGarantieToDeleteOpt = pgr.findById(id);

        if (pretGarantieToDeleteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pgr.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/curent")
    public SGRPretGarantie getLatestPretGarantie() {
        return pgr.findTopByOrderByInEffectSinceDesc();
    }

}
