package com.zegline.sgrspring.model.business.dto;

import com.zegline.sgrspring.model.filter.SGRFilterSelected;

import java.util.List;

public class SGRItemRetreivePostBody {
    private List<SGRFilterSelected> filters;

    public List<SGRFilterSelected> getFilters() {
        return filters;
    }
}
