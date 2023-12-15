package com.zegline.sgrspring.repository.business;

import com.zegline.sgrspring.model.business.SGRPurchase;
import com.zegline.sgrspring.model.business.SGRStore;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface SGRPurchaseRepository extends CrudRepository<SGRPurchase, String> {

    List<SGRPurchase> getSGRPurchasesByStoreAndPurchaseTimestampIsBetween(
            SGRStore store, Date startDate, Date endDate
    );

}
