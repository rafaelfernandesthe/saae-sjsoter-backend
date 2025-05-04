package com.saae.backend.entities;

import com.saae.backend.entities.enums.MetodoPagamento;
import com.saae.backend.entities.enums.StatusPagamento;

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
@Table(name = "pagamento")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_id_conta")
	private Conta conta;

	@Enumerated(EnumType.STRING)
	private MetodoPagamento metodo;

	@Enumerated(EnumType.STRING)
	private StatusPagamento status;

}
