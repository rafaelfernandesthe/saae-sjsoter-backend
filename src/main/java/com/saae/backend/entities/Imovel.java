package com.saae.backend.entities;

import java.util.List;

import com.saae.backend.entities.enums.StatusImovel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "imovel")
public class Imovel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_legado")
	private Long idLegado;

	private String nome;

	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	private String tipo;

	private String rua;

	private String numero;

	private String bairro;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private StatusImovel status;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Conta> contas;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "imovel_beneficio", joinColumns = @JoinColumn(name = "fk_id_imovel"), inverseJoinColumns = @JoinColumn(name = "fk_id_beneficio"))
	private List<Beneficio> beneficios;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "imovel_taxa_imposto", joinColumns = @JoinColumn(name = "fk_id_imovel"), inverseJoinColumns = @JoinColumn(name = "fk_id_taxa_imposto"))
	private List<TaxaImposto> taxaImpostos;

}
