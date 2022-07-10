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

import dev.fernandes.dave.algamoney.api.model.Cidade;
import dev.fernandes.dave.algamoney.api.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository cidadeRespository;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Cidade> findAll() {
		return cidadeRespository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("isAuthenticated()")
	public List<Cidade> findByEstadoId(@PathVariable Integer idEstado) {
		return cidadeRespository.findByEstadoId(idEstado);
	}

	@PostMapping 
	@PreAuthorize("hasAnyRole('CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Cidade> create(@Valid @RequestBody Cidade objDTO) {
		Cidade newObj = cidadeRespository.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

//	@GetMapping(value = "/{id}")
//	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')")
//	public ResponseEntity<Optional<Cidade>> findById(@PathVariable Integer id) {
//		Optional<Cidade> obj = cidadeRespository.findById(id);
//		return obj.isPresent() ? ResponseEntity.ok().body(obj) : ResponseEntity.notFound().build();
//	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Cidade> delete(@PathVariable Integer id) {
		cidadeRespository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
