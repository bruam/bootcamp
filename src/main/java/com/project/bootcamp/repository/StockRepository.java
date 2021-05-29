package com.project.bootcamp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	Optional<Stock> findByNameAndDate(String name, LocalDate date);

	// Retona stock com mesmo nome, data e id diferente
	@Query("SELECT stock FROM Stock stock WHERE stock.name = :name AND stock.date = :date AND stock.id <> :id")
	Optional<Stock> findByStockUpdate(String name, LocalDate date, Long id);

	@Query("SELECT stock FROM Stock stock WHERE stock.date = :date ")
	Optional<List<Stock>> findByToday(LocalDate date);

}
