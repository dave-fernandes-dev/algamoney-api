package dev.fernandes.dave.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import dev.fernandes.dave.algamoney.api.model.Pessoa;
import dev.fernandes.dave.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa update(@PathVariable int id, @RequestBody Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

		  BeanUtils.copyProperties(pessoa, pessoaSalva, "id");

		  return this.pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa updateStatus(@PathVariable int id, @RequestBody Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

		  //muda status
		  pessoaSalva.setAtivo(!pessoaSalva.isAtivo());

		  return this.pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa updateAtivar(@PathVariable int id, @RequestBody Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

		  //muda status
		  pessoaSalva.setAtivo(true);

		  return this.pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa updateDesativar(@PathVariable int id, @RequestBody Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

		  //muda status
		  pessoaSalva.setAtivo(false);

		  return this.pessoaRepository.save(pessoaSalva);
	}

}
