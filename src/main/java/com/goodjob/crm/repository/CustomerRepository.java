package com.goodjob.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import com.goodjob.crm.model.Customer;
import com.goodjob.crm.model.QCustomer;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface CustomerRepository extends JpaRepository<Customer, Long>,
											QuerydslPredicateExecutor<Customer>,
											QuerydslBinderCustomizer<QCustomer>  {
	
	@Override
    default public void customize(QuerydslBindings bindings, QCustomer root) {	
		bindings.bind(String.class)
        .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.emailAddress);
		bindings.excluding(root.mobileNo);
	}
	

}
