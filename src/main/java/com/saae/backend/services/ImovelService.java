package com.saae.backend.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saae.backend.entities.Imovel;
import com.saae.backend.repositories.ImovelRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class ImovelService {

	@Autowired
	private ImovelRepository imovelRepository;

	@Cacheable(value = "imoveisPaginados", key = "T(String).valueOf(#pageable.pageNumber) + '-' + T(String).valueOf(#pageable.pageSize) + '-' + T(String).valueOf(#tipo) + '-' + T(String).valueOf(#rua) + '-' + T(String).valueOf(#numero) + '-' + T(String).valueOf(#bairro) + '-' + T(String).valueOf(#proprietario) + '-' + T(String).valueOf(#cpfCnpj)")
	public Page<Imovel> listarImoveis(Pageable pageable, String tipo, String rua, String numero, String bairro,
			String proprietario, String cpfCnpj) {
		return imovelRepository.findAll((root, query, criteriaBuilder) -> {
			var predicates = new ArrayList<Predicate>();

			if (StringUtils.hasText(tipo)) {
				predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
			}
			if (StringUtils.hasText(rua)) {
				predicates.add(criteriaBuilder.like(root.get("rua"), "%" + rua + "%"));
			}
			if (StringUtils.hasText(numero)) {
				predicates.add(criteriaBuilder.equal(root.get("numero"), numero));
			}
			if (StringUtils.hasText(bairro)) {
				predicates.add(criteriaBuilder.like(root.get("bairro"), "%" + bairro + "%"));
			}
			if (StringUtils.hasText(proprietario)) {
				predicates.add(criteriaBuilder.like(root.get("nome"), "%" + proprietario + "%"));
			}
			if (StringUtils.hasText(cpfCnpj)) {
				predicates.add(criteriaBuilder.like(root.get("cpfCnpj"), "%" + cpfCnpj.replaceAll("[^0-9]", "")+ "%"));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		}, pageable);
	}

	public Optional<Imovel> obterImovelPorId(Long id) {
		return imovelRepository.findById(id);
	}

	public Imovel criarImovel(Imovel imovel) {
		return imovelRepository.save(imovel);
	}

	public Imovel atualizarImovel(Long id, Imovel imovel) {
		if (imovelRepository.existsById(id)) {
			imovel.setId(id);
			return imovelRepository.save(imovel);
		}
		return null;
	}

	public boolean deletarImovel(Long id) {
		if (imovelRepository.existsById(id)) {
			imovelRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
