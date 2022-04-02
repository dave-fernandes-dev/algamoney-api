package dev.fernandes.dave.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.fernandes.dave.algamoney.api.exceptions.ObjectnotFoundException;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;

@Service
public class LancamentoService {
	

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento findById(Integer id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}

	public Lancamento create(Lancamento objDTO) {
		objDTO.setId(0);
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
