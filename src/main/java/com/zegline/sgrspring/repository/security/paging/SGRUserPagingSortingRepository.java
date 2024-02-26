package com.zegline.sgrspring.repository.security.paging;

import com.zegline.sgrspring.model.security.SGRUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SGRUserPagingSortingRepository extends PagingAndSortingRepository<SGRUser, String> {
}
