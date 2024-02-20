package com.zegline.sgrspring.repository.business.paging;

import com.zegline.sgrspring.model.business.SGRStore;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SGRStorePagingSortingRepository extends PagingAndSortingRepository<SGRStore, String> {
}
