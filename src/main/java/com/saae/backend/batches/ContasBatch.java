package com.saae.backend.batches;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.saae.backend.services.ContaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContasBatch {
	
	@Autowired
	private ContaService contaService;
	
	@Scheduled(cron = "0 5 0 1-5 * ?")
	public void gerarContas() {
		log.info("Gerando contas...");
		boolean temFaturaGerada = contaService.temContaGerada(LocalDateTime.now());
		
		if(!temFaturaGerada) {
			contaService.gerarContas(LocalDateTime.now());
			log.info("Todas Contas geradas com sucesso.");
		} else {
			log.info("Já existe fatura gerada para o mês atual.");
		}
	}

}
