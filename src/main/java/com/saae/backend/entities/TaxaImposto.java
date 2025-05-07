package com.saae.backend.entities;

import java.math.BigDecimal;
import java.util.List;

import com.saae.backend.entities.enums.StatusTaxaImposto;
import com.saae.backend.entities.enums.TipoTaxaImposto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "taxa_imposto")
public class TaxaImposto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoTaxaImposto tipo;

	@Column(name = "valor_aplicado")
	private BigDecimal valorAplicado;

	private StatusTaxaImposto status;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "taxasImpostos")
	private List<Imovel> imoveis;

}
