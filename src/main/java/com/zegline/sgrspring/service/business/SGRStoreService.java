package com.zegline.sgrspring.service.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.business.SGRPurchase;
import com.zegline.sgrspring.model.business.SGRStore;
import com.zegline.sgrspring.repository.business.SGRPurchaseRepository;
import com.zegline.sgrspring.repository.business.SGRStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SGRStoreService {

    @Autowired
    SGRStoreRepository sr;

    @Autowired
    SGRPurchaseRepository pr;

    @Autowired
    SGRPurchaseService ps;

    public double getGarantieForPeriod(SGRStore store, Date start, Date end) {
        // Get purchases in date period
        List<SGRPurchase> purchases = pr.getSGRPurchasesByStoreAndPurchaseTimestampIsBetween(store, start, end);

        double garantieTotal = 0.0;

        // Calculate for each the garantie at the time
        for (SGRPurchase p : purchases) {
            double garantie = ps.getGarantiePriceForPurchase(p);
            garantieTotal += garantie;
        }

        return garantieTotal;
    }

    public SGRPurchase createPurchase(SGRStore store, SGRItem item) {
        SGRPurchase newPurchase = ps.createPurchaseFromItem(item);
        newPurchase.setStore(store);

        return newPurchase;
    }

    public SGRStore createStore(String name, String desc) {
        SGRStore newStore = new SGRStore(name, desc);
        return sr.save(newStore);
    }

}
