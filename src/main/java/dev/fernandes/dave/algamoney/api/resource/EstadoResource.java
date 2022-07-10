package dev.fernandes.dave.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.model.Estado;
import dev.fernandes.dave.algamoney.api.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

	@Autowired
	private EstadoRepository estadoRespository;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Estado> findAll() {
		return estadoRespository.findAll();
	}

	@PostMapping 
	@PreAuthorize("hasAnyRole('CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Estado> create(@Valid @RequestBody Estado objDTO) {
		Estado newObj = estadoRespository.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Optional<Estado>> findById(@PathVariable Integer id) {
		Optional<Estado> obj = estadoRespository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok().body(obj) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Estado> delete(@PathVariable Integer id) {
		estadoRespository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
