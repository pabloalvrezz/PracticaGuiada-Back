package com.izertis.baseapp.service.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.izertis.abstractions.filter.AbstractJpaSpecification;
import com.izertis.abstractions.filter.EntityFilter;
import com.izertis.baseapp.service.model.Product;
import com.izertis.baseapp.service.model.Product_;
import com.izertis.baseapp.service.model.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilter extends AbstractJpaSpecification<Product> implements EntityFilter {

	private final Product product = new Product();

	private String name;
	private String description;
	private int stock;
	private Boolean enable;
	private double activePrice;
	private Type tipo;
	
	@Override
	public Predicate toPredicate(final Root<Product> root, final CriteriaQuery<?> query,
			final CriteriaBuilder criteriaBuilder) {

		final List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(this.name))
			predicates
					.add(this.createContainsIgnoreCase(root, criteriaBuilder, Product_.NAME, this.name));
		
		if (StringUtils.isNotBlank(this.description))
			predicates
					.add(this.createContainsIgnoreCase(root, criteriaBuilder, Product_.DESCRIPTION, this.description));

		if (stock > 0)
			predicates.add(this.createEquals(root, criteriaBuilder, Product_.STOCK, this.stock));

		if (this.enable != null)
			predicates.add(this.createEquals(root, criteriaBuilder, Product_.ENABLED, this.enable));
		
		if(this.activePrice != 0)
		    predicates.add(this.createEquals(root, criteriaBuilder, Product_.ACTIVE_PRICE, this.activePrice));
		
		if(tipo != null)
		    predicates.add(this.createEquals(root, criteriaBuilder, Product_.TIPO, this.tipo));
		
		return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
	}

}
