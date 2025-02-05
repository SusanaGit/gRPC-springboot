package com.susanafigueroa.userservice.repository;

import com.susanafigueroa.common.Ticker;
import com.susanafigueroa.userservice.entity.PortfolioItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioItemRepository extends CrudRepository<PortfolioItem, Integer> {

    // it will automatically create the SQL queries
    // devuelve un listado de todos los objetos PortfolioItem que tengan el userId pasado por parámetro
    List<PortfolioItem> findAllByUserId(Integer userId);

    // it will automatically create the SQL queries
    // devuelve un PortfolioItem (si existe) que tenga el userId y el ticker pasados por parámetro
    Optional<PortfolioItem> findByUserIdAndTicker(Integer userId, Ticker ticker);

}
