package com.izertis.baseapp.service.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import com.izertis.abstractions.filter.AbstractJpaSpecification;
import com.izertis.abstractions.filter.EntityFilter;
import com.izertis.baseapp.service.model.Brand;
import com.izertis.baseapp.service.model.Brand_;
import com.izertis.baseapp.service.model.Product_;

public class BrandFilter extends AbstractJpaSpecification<Brand> implements EntityFilter {

    private String name;

    private String location;

    private double marketValue;

    private String CEO;

    private String sector;

    @Override
    public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(this.name))
            predicates.add(this.createContainsIgnoreCase(root, criteriaBuilder, Brand_.NAME, this.name));

        if (StringUtils.isNotBlank(this.location))
            predicates.add(this.createContainsIgnoreCase(root, criteriaBuilder, Brand_.LOCATION, this.location));

        if (StringUtils.isNotBlank(this.CEO))
            predicates.add(this.createContainsIgnoreCase(root, criteriaBuilder, Brand_.C_EO, this.sector));

        if (StringUtils.isNotBlank(this.sector))
            predicates.add(this.createContainsIgnoreCase(root, criteriaBuilder, Brand_.SECTOR, this.sector));

        if (marketValue != 0)
            predicates.add(this.createEquals(root, criteriaBuilder, Brand_.MARKET_VALUE, this.marketValue));

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }

}
