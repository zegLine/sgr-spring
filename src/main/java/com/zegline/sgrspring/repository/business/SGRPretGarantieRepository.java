package com.zegline.sgrspring.repository.business;

import com.zegline.sgrspring.model.business.SGRPretGarantie;
import org.springframework.data.repository.CrudRepository;

public interface SGRPretGarantieRepository extends CrudRepository<SGRPretGarantie, Long> {

    SGRPretGarantie findTopByOrderByInEffectSinceDesc();

}
