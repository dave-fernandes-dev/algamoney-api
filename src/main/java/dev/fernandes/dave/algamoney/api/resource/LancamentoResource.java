package dev.fernandes.dave.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;
import dev.fernandes.dave.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
	public ResponseEntity<Lancamento> findById(@PathVariable Integer id) {
		Lancamento obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
	public ResponseEntity<List<Lancamento>> pesquisar(LancamentoFilter filter) {
		List<Lancamento> list = service.filtrar(filter);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/paginados")
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read') ")
	public Page<Lancamento> pesquisarPaginado(LancamentoFilter filter, Pageable pageable) {
		Page<Lancamento> list = service.filtrarPaginado(filter, pageable);
		return list;
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write') ")
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento objDTO) {
		Lancamento newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write') ")
	public ResponseEntity<Lancamento> update(@PathVariable Integer id, @Valid @RequestBody Lancamento objDTO) {
		Lancamento obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('REMOVER_LANCAMENTO') and #oauth2.hasScope('write') ")
	public ResponseEntity<Lancamento> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
