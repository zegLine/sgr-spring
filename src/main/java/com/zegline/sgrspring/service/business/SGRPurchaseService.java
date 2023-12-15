package com.zegline.sgrspring.service.business;

import com.zegline.sgrspring.model.business.SGRPretGarantie;
import com.zegline.sgrspring.model.business.SGRPurchase;
import com.zegline.sgrspring.repository.business.SGRPretGarantieRepository;
import com.zegline.sgrspring.repository.business.SGRPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SGRPurchaseService {

    @Autowired
    SGRPurchaseRepository pr;

    @Autowired
    SGRPretGarantieRepository pgr;

    public Optional<SGRPurchase> getPurchaseById(String id) {
        return pr.findById(id);
    }

    public SGRPretGarantie getGarantieObjForPurchase(SGRPurchase purchase) {
        Date purchaseDate = purchase.getPurchase_timestamp();

        SGRPretGarantie priceAtPurchase = pgr.findFirstByInEffectSinceLessThanEqualOrderByInEffectSinceDesc(purchaseDate);

        return priceAtPurchase;
    }

    public double getGarantiePriceForPurchase(SGRPurchase purchase){
        SGRPretGarantie priceAtPurchase = getGarantieObjForPurchase(purchase);

        return priceAtPurchase.getPrice();
    }

}
