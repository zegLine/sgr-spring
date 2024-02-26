package com.zegline.sgrspring.service.business;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.filter.SGRFilterSelected;
import com.zegline.sgrspring.repository.business.SGRItemRepository;
import com.zegline.sgrspring.repository.business.paging.SGRItemPagingSortingRepository;
import com.zegline.sgrspring.service.business.specification.SGRItemSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SGRItemService {

    @Autowired
    private SGRItemRepository ir;

    @Autowired
    private SGRItemPagingSortingRepository itemPagingSortingRepository;

    public List<SGRItem> getItems() {
        return (List<SGRItem>) ir.findAll();
    }

    private Specification<SGRItem> getCombinedSpecification(List<SGRFilterSelected> filters) {
        Specification<SGRItem> combinedSpecification = Specification.where(null);

        // Combine all filters into a single specification using conjunctions
        for (SGRFilterSelected filterSelected : filters) {
            Specification<SGRItem> filterSpecification = new SGRItemSpecification(filterSelected);
            combinedSpecification = combinedSpecification.and(filterSpecification);
        }

        return combinedSpecification;
    }

    public Page<SGRItem> getItemsPaginated(int pageSize, int pageNumber, List<SGRFilterSelected> filtersSelected) {
        Specification<SGRItem> combinedSpecification = getCombinedSpecification(filtersSelected);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return itemPagingSortingRepository.findAll(combinedSpecification, pageRequest);
    }

    public Page<SGRItem> getItemsPaginatedSorted(int pageSize, int pageNumber, String sortingColumn, Sort.Direction sortingDirection, List<SGRFilterSelected> filtersSelected) {
        Specification<SGRItem> combinedSpecification = getCombinedSpecification(filtersSelected);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortingColumn));

        return itemPagingSortingRepository.findAll(combinedSpecification, pageRequest);
    }

    public Optional<SGRItem> getItemById(String id) {
        return ir.findById(id);
    }

    public SGRItem createItem(String name, double weight) {
        SGRItem item = new SGRItem(name, weight);

        return ir.save(item);
    }

    public Optional<SGRItem> deleteItem(String itemId) {
        Optional<SGRItem> itemToDelete = ir.findById(itemId);

        if (itemToDelete.isEmpty()) {
            return Optional.empty();
        }

        ir.deleteById(itemId);

        return itemToDelete;
    }

}
