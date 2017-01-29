package com.ebe.SearchSpecifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by saado on 11/23/2016.
 */
public class GenericSpecification<T> implements Specification<T> {

    public static final String SpecificationsPattern = "(\\w+?(\\.\\w+)?)(=|:|<|>|(>=)|(<=))(\\w+?( \\w+)?),";
    public static final int fieldNameIndex = 1;
    public static final int operatorIndex = 3;
    public static final int valueIndex = 6;


    private SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String[] propertyParts = criteria.getKey().split("\\.");
        Path property = null;
        if (propertyParts.length == 0) { //single property
            property = root.get(criteria.getKey());
        } else { //nested properties ex. region.regionName

            property = root.get(propertyParts[0]);
            for (int i = 1; i < propertyParts.length; i++) {
                property = property.get(propertyParts[i]);
            }
        }
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(property, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(property, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(property, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(property, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(property, criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (property.getJavaType() == String.class) {
                return builder.like(property, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(property, criteria.getValue());
            }
        }
        return null;
    }
}
