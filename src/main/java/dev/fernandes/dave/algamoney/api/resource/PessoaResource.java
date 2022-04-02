package dev.fernandes.dave.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.events.RecursoCriadoEvent;
import dev.fernandes.dave.algamoney.api.model.Pessoa;
import dev.fernandes.dave.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PessoaRepository pessoaRespository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Pessoa>> findById(@PathVariable Integer id) {
		Optional<Pessoa> obj = pessoaRespository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok().body(obj) : ResponseEntity.notFound().build();
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
	
	@DeleteMapping(value = "/{id}") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		pessoaRespository.deleteById(id);	
	}
}
