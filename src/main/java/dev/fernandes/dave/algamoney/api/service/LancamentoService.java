package dev.fernandes.dave.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.exceptions.ObjectnotFoundException;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;

@Service
public class LancamentoService {
	

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;

	public Lancamento findById(Integer id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	public List<Lancamento> filtrar(LancamentoFilter filter) {
		return lancamentoRepository.filtrar(filter);
	}
	
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.filtrarPaginado(filter, pageable);
	}
	
	public Page<ResumoLancamento> resumirPaginado(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.resumirPaginado(filter, pageable);
	}


	public Lancamento create(Lancamento objDTO) {
		objDTO.setId(0);
		
		if (pessoaService.isInativa(objDTO.getPessoa().getId())) {
			throw new DataIntegrityViolationException("Não pode Cadastrar Lançamento com pessoa Inativa");
		}
		return lancamentoRepository.save(objDTO);
	}

	public Lancamento update(Integer id, @Valid Lancamento objDTO) {
		objDTO.setId(id);
		Lancamento oldObj = findById(id);
		BeanUtils.copyProperties(objDTO, oldObj, "id");
		return lancamentoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Lancamento obj = findById(id);
		lancamentoRepository.deleteById(obj.getId());
	}

}
