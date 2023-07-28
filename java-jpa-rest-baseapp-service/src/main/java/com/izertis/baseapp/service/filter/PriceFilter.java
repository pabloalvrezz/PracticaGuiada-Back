package com.izertis.baseapp.service.filter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.izertis.abstractions.filter.AbstractJpaSpecification;
import com.izertis.abstractions.filter.EntityFilter;
import com.izertis.baseapp.service.model.Prices;
import com.izertis.baseapp.service.model.Prices_;
import com.izertis.baseapp.service.model.Product;

public class PriceFilter extends AbstractJpaSpecification<Prices> implements EntityFilter{

	private double cuantity;
	private Date startDate;
	private Date endDate;
		
	@Override
	public Predicate toPredicate(Root<Prices> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		final List<Predicate> predicates = new ArrayList<>();
		
		if(cuantity != 0)
			predicates.add(this.createEquals(root, criteriaBuilder,Prices_.CUANTITY, this.cuantity));
		
		return criteriaBuilder.and(predicates.stream().toArray(Predicate[]:: new));
	}

}
