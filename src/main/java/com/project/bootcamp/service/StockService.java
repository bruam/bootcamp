package com.project.bootcamp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.mapper.StockMapper;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;

@Service
public class StockService {

	@Autowired
	private StockRepository repository;
	
	@Autowired
	private StockMapper mapper;
	
	@Transactional //Abre transação para manipular banco, de forma automatica
	public StockDTO save(StockDTO dto) {
		
		Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
		//se dto já existe, propaga mensagem de erro
		if(optionalStock.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		
		Stock stock = mapper.toEntity(dto);
		repository.save(stock);
		return mapper.toDto(stock);
	}

	@Transactional
	public StockDTO update(StockDTO dto) {
		Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
		//se dto já existe, propaga mensagem de erro
		if(optionalStock.isPresent()) {
			throw new BusinessException(MessageUtils.STOCK_ALREADY_EXISTS);
		}
		Stock stock = mapper.toEntity(dto);
		repository.save(stock);
		return mapper.toDto(stock);
	}

	@Transactional(readOnly = true)
	public List<StockDTO> findAll() {		
		return mapper.toDto(repository.findAll());
	}

	@Transactional(readOnly = true)
	public StockDTO findById(Long id) {
		return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}

	@Transactional
	public StockDTO delete(Long id) {
		StockDTO dto = this.findById(id); //Verifica se dto existe		
		repository.deleteById(dto.getId()); //Se existe deleta
		return dto; // Retorna dto encontrado e deletado
	}

	@Transactional(readOnly = true)
	public List<StockDTO> findByToday() {
		return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);
	}
	
	

}
