package com.zegline.sgrspring.repository.business.paging;

import com.zegline.sgrspring.model.business.SGRItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SGRItemPagingSortingRepository extends PagingAndSortingRepository<SGRItem, String> {
}
