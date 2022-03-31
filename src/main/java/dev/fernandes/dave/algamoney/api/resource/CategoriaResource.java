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

import dev.fernandes.dave.algamoney.api.model.Categoria;
import dev.fernandes.dave.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRespository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Categoria>> findById(@PathVariable Integer id) {
		Optional<Categoria> obj = categoriaRespository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok().body(obj) : ResponseEntity.notFound().build();
	}

	@GetMapping
	public List<Categoria> findAll() {
		return categoriaRespository.findAll();
	}

	@PostMapping    //SEM event publisher
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria objDTO) {
		Categoria newObj = categoriaRespository.save(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
}
