package com.saae.backend.services;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.saae.backend.entities.Conta;
import com.saae.backend.entities.Imovel;
import com.saae.backend.entities.enums.StatusFatura;
import com.saae.backend.entities.enums.TipoBeneficio;
import com.saae.backend.entities.enums.TipoTaxaImposto;
import com.saae.backend.repositories.ContaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    
    @Autowired
    private ImovelService imovelRepository;

    public List<Conta> listar() {
        return contaRepository.findAll();
    }

    public List<Conta> listarPorImovel(Long imovelId) {
        return contaRepository.findByImovelId(imovelId);
    }

    public Optional<Conta> obterPorId(Long id) {
        return contaRepository.findById(id);
    }

    public Conta criar(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta atualizar(Long id, Conta conta) {
        if (contaRepository.existsById(id)) {
            conta.setId(id);
            return contaRepository.save(conta);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (contaRepository.existsById(id)) {
        	contaRepository.deleteById(id);
            return true;
        }
        return false;
    }

	public boolean temContaGerada(LocalDateTime now) {
		
		String mesReferencia = String.format("%02d", now.getMonthValue()) + "/" + now.getYear();
		
		Long qtd = contaRepository.countByMesReferencia(mesReferencia);
		
		return qtd > 0;
	}
	
	public void calcularValorConta(Conta conta) {
		
		Imovel imovel = conta.getImovel();
		
		imovel.getTaxasImpostos().stream().filter(t -> TipoTaxaImposto.VALOR.equals(t.getTipo())).forEach(taxaImposto -> {
			BigDecimal valorTotal = conta.getValorTotal();
			valorTotal = valorTotal.add(taxaImposto.getValorAplicado());
		});
		imovel.getTaxasImpostos().stream().filter(t -> TipoTaxaImposto.PERCENTUAL.equals(t.getTipo())).forEach(taxaImposto -> {
			BigDecimal valorTotal = conta.getValorTotal();
			BigDecimal acrescimo = valorTotal.multiply(taxaImposto.getValorAplicado()).divide(BigDecimal.valueOf(100));
	        valorTotal = valorTotal.add(acrescimo);
		});
		
		if(conta.getValorTotal().compareTo(new BigDecimal(0)) <= 0) {
			conta.setValorTotal(new BigDecimal(0));
			return;
		}
		
		imovel.getBeneficios().stream().filter(t -> TipoBeneficio.VALOR.equals(t.getTipo())).forEach(beneficio -> {
			BigDecimal valorTotal = conta.getValorTotal();
			valorTotal = valorTotal.add(beneficio.getDescontoAplicado().negate());
		});
		imovel.getBeneficios().stream().filter(t -> TipoBeneficio.PERCENTUAL.equals(t.getTipo())).forEach(beneficio -> {
			BigDecimal valorTotal = conta.getValorTotal();
			BigDecimal desconto = valorTotal.multiply(beneficio.getDescontoAplicado()).divide(BigDecimal.valueOf(100));
	        valorTotal = valorTotal.subtract(desconto);
		});
		
	}

	public void gerarContas(LocalDateTime dateTime) {
		
		String mesReferencia = String.format("%02d", dateTime.getMonthValue()) + "/" + dateTime.getYear();
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
		
		while(true) {
			
			log.info("INICIO - Gerando contas para o mês: {} pagina: {} ", mesReferencia, pageable.getPageNumber());
			var imoveis = imovelRepository.listar(pageable, null, null, null, null, null, null);
			
			var contasList = imoveis.stream().parallel().map(imovel -> {
				Conta conta = new Conta();
				conta.setImovel(imovel);
				conta.setMesReferencia(mesReferencia);
				conta.setDataGeracao(LocalDateTime.now());
				conta.setDataVencimento(LocalDate.now().withDayOfMonth(20));//supondo que o vencimento é todo dia 20
				conta.setStatus(StatusFatura.PENDENTE);
				
				this.calcularValorConta(conta);
				
				return conta;
				
			}).collect(Collectors.toList());
			
			contaRepository.saveAll(contasList);
			
			log.info("FIM - Gerando contas para o mês: {} pagina: {}/{} ", mesReferencia, pageable.getPageNumber(), imoveis.getTotalPages());
			
			if(imoveis.isLast()) {
				break;
			}
			
			pageable = pageable.next();
			
		}
		
	}
}
