package dev.fernandes.dave.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.model.Pessoa;
import dev.fernandes.dave.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

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

	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa objDTO) {
		Pessoa newObj = pessoaRespository.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
}
