package com.payconiq.stockDemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.payconiq.stockDemo.dto.Stock;

@EnableJpaRepositories
public interface StockRepository extends JpaRepository<Stock, Integer>{

}
