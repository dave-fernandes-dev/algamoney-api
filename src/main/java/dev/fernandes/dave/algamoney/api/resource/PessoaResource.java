package dev.fernandes.dave.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.fernandes.dave.algamoney.api.events.RecursoCriadoEvent;
import dev.fernandes.dave.algamoney.api.model.Pessoa;
import dev.fernandes.dave.algamoney.api.repository.PessoaRepository;
import dev.fernandes.dave.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaRepository pessoaRespository;
	
	@Autowired
	PessoaService pessoaService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable Integer id) {
		Optional<Pessoa> obj = pessoaRespository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public List<Pessoa> findAll() {
		return pessoaRespository.findAll();
	}

	@PostMapping  //COM event publisher
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa objDTO, HttpServletResponse response) {
		Pessoa newObj = pessoaRespository.save(objDTO);
		
		//usando listener e event
		publisher.publishEvent(new RecursoCriadoEvent(this, response, newObj.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Integer id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa obj = pessoaService.update(id, pessoa);
		return ResponseEntity.ok(obj);
	}

	@DeleteMapping(value = "/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		pessoaRespository.deleteById(id);	
	}
	
	@PutMapping(value = "/{id}/mudar-status")
	public ResponseEntity<Pessoa> updateStatus(@PathVariable Integer id, Pessoa pessoa) {
		Pessoa obj = pessoaService.updateStatus(id, pessoa);
		return ResponseEntity.ok(obj);
	}
	
	@PutMapping(value = "/{id}/ativar")
	public ResponseEntity<Pessoa> updateAtivar(@PathVariable Integer id, Pessoa pessoa) {
		Pessoa obj = pessoaService.updateAtivar(id, pessoa);
		return ResponseEntity.ok(obj);
	}
	
	@PutMapping(value = "/{id}/desativar")
	public ResponseEntity<Pessoa> updateDesativar(@PathVariable Integer id, Pessoa pessoa) {
		Pessoa obj = pessoaService.updateDesativar(id, pessoa);
		return ResponseEntity.ok(obj);
	}
	
}
