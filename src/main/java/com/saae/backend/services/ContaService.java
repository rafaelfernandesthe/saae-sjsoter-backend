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

    // Listar todas as contas
    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    // Listar as contas de um imóvel
    public List<Conta> listarContasPorImovel(Long imovelId) {
        return contaRepository.findByImovelId(imovelId);
    }

    // Obter uma conta por ID
    public Optional<Conta> obterContaPorId(Long id) {
        return contaRepository.findById(id);
    }

    // Criar uma nova conta
    public Conta criarConta(Conta conta) {
        return contaRepository.save(conta);
    }

    // Atualizar uma conta existente
    public Conta atualizarConta(Long id, Conta conta) {
        if (contaRepository.existsById(id)) {
            conta.setId(id);
            return contaRepository.save(conta);
        }
        return null;
    }

    // Deletar uma conta
    public boolean deletarConta(Long id) {
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
			conta.getValorTotal().add(taxaImposto.getValorAplicado());
		});
		imovel.getTaxasImpostos().stream().filter(t -> TipoTaxaImposto.PERCENTUAL.equals(t.getTipo())).forEach(taxaImposto -> {
			conta.getValorTotal().add(taxaImposto.getValorAplicado());
		});
		
		if(conta.getValorTotal().compareTo(new BigDecimal(0)) <= 0) {
			return;
		}
		
		imovel.getBeneficios().stream().filter(t -> TipoBeneficio.VALOR.equals(t.getTipo())).forEach(beneficio -> {
			conta.getValorTotal().add(beneficio.getDescontoAplicado().negate());
		});
		imovel.getBeneficios().stream().filter(t -> TipoBeneficio.PERCENTUAL.equals(t.getTipo())).forEach(beneficio -> {
			conta.getValorTotal().add(beneficio.getDescontoAplicado().negate());
		});
		
	}

	public void gerarContas(LocalDateTime dateTime) {
		
		String mesReferencia = String.format("%02d", dateTime.getMonthValue()) + "/" + dateTime.getYear();
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "id"));
		
		while(true) {
			
			log.info("INICIO - Gerando contas para o mês: {} pagina: {} ", mesReferencia, pageable.getPageNumber());
			var imoveis = imovelRepository.listarImoveis(pageable, null, null, null, null, null, null);
			
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
