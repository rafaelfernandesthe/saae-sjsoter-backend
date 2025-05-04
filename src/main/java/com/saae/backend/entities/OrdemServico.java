package com.saae.backend.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.saae.backend.entities.enums.StatusOrdemServico;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dataGeracao;

	private LocalDate dataPrevistaExecucao;
	
	private LocalDateTime dataExecucao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_id_conta", columnDefinition = "ordemServicos")
	private Conta conta;

	@Enumerated(EnumType.STRING)
	private StatusOrdemServico status;

}
