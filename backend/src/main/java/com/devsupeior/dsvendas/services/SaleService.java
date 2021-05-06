package com.devsupeior.dsvendas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsupeior.dsvendas.dto.SaleDTO;
import com.devsupeior.dsvendas.dto.SaleSuccessDTO;
import com.devsupeior.dsvendas.dto.SaleSumDTO;
import com.devsupeior.dsvendas.entities.Sale;
import com.devsupeior.dsvendas.repositories.SaleRepository;
import com.devsupeior.dsvendas.repositories.SellerRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Transactional(readOnly = true) //evita lock no banco para consultas de leitura
	public Page<SaleDTO> findAll(Pageable pageable) {
		
		sellerRepository.findAll();//todos os vendedores irão ficar em memória evitando que a JPA faça uma consulta separada para cada seller
		Page<Sale> result = saleRepository.findAll(pageable);
		return result.map(x -> new SaleDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<SaleSumDTO> amountGroupedBySeller() {
		return saleRepository.amountGroupedBySeller();
	}
	
	@Transactional(readOnly = true)
	public List<SaleSuccessDTO> successGroupedBySeller() {
		return saleRepository.successGroupedBySeller();
	}
}
