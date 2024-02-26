package com.zegline.sgrspring.service.business.specification;

import com.zegline.sgrspring.model.business.SGRItem;
import com.zegline.sgrspring.model.filter.SGRFilterSelected;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SGRItemSpecification implements Specification<SGRItem> {
    private final SGRFilterSelected filterSelected;

    public SGRItemSpecification(SGRFilterSelected filter) {
        filterSelected = filter;
    }

    @Override
    public Predicate toPredicate(Root<SGRItem> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (filterSelected.filter().filter_type()) {
            case STRING:
                return switch (filterSelected.predicate()) {
                    case "CONTAINS" ->
                            criteriaBuilder.like(root.get(filterSelected.filter().column_name()), "%" + filterSelected.value() + "%");
                    case "IS" ->
                            criteriaBuilder.equal(root.get(filterSelected.filter().column_name()), filterSelected.value());
                    case "IS_NOT" ->
                            criteriaBuilder.notEqual(root.get(filterSelected.filter().column_name()), filterSelected.value());
                    default ->
                            throw new IllegalArgumentException("Unsupported string predicate: " + filterSelected.predicate());
                };
            case NUMERIC:
                return switch (filterSelected.predicate()) {
                    case "GREATER_THAN" ->
                            criteriaBuilder.greaterThan(root.get(filterSelected.filter().column_name()), Double.parseDouble(filterSelected.value()));
                    case "LESS_THAN" ->
                            criteriaBuilder.lessThan(root.get(filterSelected.filter().column_name()), Double.parseDouble(filterSelected.value()));
                    case "EQUALS" ->
                            criteriaBuilder.equal(root.get(filterSelected.filter().column_name()), Double.parseDouble(filterSelected.value()));
                    default ->
                            throw new IllegalArgumentException("Unsupported numeric predicate: " + filterSelected.predicate());
                };
            default:
                throw new IllegalArgumentException("Unsupported filter type: " + filterSelected.filter().filter_type());
        }
    }
}
