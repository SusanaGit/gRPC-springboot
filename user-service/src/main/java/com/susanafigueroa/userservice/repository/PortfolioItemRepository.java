package com.susanafigueroa.userservice.repository;

import com.susanafigueroa.userservice.entity.PortfolioItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioItemRepository extends CrudRepository<PortfolioItem, Integer> {
}
