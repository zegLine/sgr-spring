package com.zegline.sgrspring.repository.business;

import com.zegline.sgrspring.model.business.SGRPretGarantie;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface SGRPretGarantieRepository extends CrudRepository<SGRPretGarantie, Long> {

    SGRPretGarantie findTopByOrderByInEffectSinceDesc();

    SGRPretGarantie findFirstByInEffectSinceLessThanEqualOrderByInEffectSinceDesc(Date timestamp);

}
