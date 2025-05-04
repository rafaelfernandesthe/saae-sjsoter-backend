package com.saae.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.saae.backend.entities.enums.StatusFatura;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "conta")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_id_imovel", nullable = false)
	private Imovel imovel;

	private BigDecimal valorTotal;

	private String mesReferencia;

	private LocalDateTime dataGeracao;

	private LocalDate dataVencimento;

	private LocalDateTime dataPagamento;

	private String metodoPagamento;

	@Enumerated(EnumType.STRING)
	private StatusFatura status;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Pagamento> pagamentos;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<OrdemServico> ordemServicos;
}
