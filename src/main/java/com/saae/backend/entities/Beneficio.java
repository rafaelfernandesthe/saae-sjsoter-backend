package com.saae.backend.entities;

import java.math.BigDecimal;
import java.util.List;

import com.saae.backend.entities.enums.StatusBeneficio;
import com.saae.backend.entities.enums.TipoBeneficio;

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
@Table(name = "beneficio")
public class Beneficio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoBeneficio tipo;

	private BigDecimal descontoAplicado;

	private StatusBeneficio status;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "beneficios")
	private List<Imovel> imoveis;

}
