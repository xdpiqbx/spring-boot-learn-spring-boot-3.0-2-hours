package com.amigoscode;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
  // JpaRepository<@Entity, Type of @Id>
}
