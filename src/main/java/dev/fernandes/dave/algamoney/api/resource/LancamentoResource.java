package dev.fernandes.dave.algamoney.api.resource;

import java.net.URI;
import java.time.LocalDate;
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

import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByCategoria;
import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByDia;
import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;
import dev.fernandes.dave.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public ResponseEntity<Lancamento> findById(@PathVariable Integer id) {
		Lancamento obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/estatisticas/por-categoria")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaByCategoria> porCategoria() {
		return this.lancamentoRepository.byCategoria(LocalDate.parse("2021-07-02"));
	}
	
	@GetMapping("/estatisticas/por-dia")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaByDia> porDia() {
		return this.lancamentoRepository.byDia(LocalDate.parse("2021-07-02"));
	}
	
	@GetMapping("/estatisticas/por-categoria-mes")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaByCategoria> porCategoriaNoMes(int mes) {
		return this.lancamentoRepository.byCategoria(LocalDate.now().withMonth(1));
	}
	
	@GetMapping("/estatisticas/por-mes")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaByCategoria> porMes(String data) {
		return this.lancamentoRepository.byCategoria(LocalDate.parse(data));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public ResponseEntity<List<Lancamento>> pesquisar(LancamentoFilter filter) {
		List<Lancamento> list = service.filtrar(filter);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/paginados")
	@PreAuthorize("hasRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public Page<Lancamento> pesquisarPaginado(LancamentoFilter filter, Pageable pageable) {
		Page<Lancamento> list = service.filtrarPaginado(filter, pageable);
		return list;
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasRole('PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public Page<ResumoLancamento> resumirPaginado(LancamentoFilter filter, Pageable pageable) {
		Page<ResumoLancamento> list = service.resumirPaginado(filter, pageable);
		return list;
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento objDTO) {
		Lancamento newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO')and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> update(@PathVariable Integer id, @Valid @RequestBody Lancamento objDTO) {
		Lancamento obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('REMOVER_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}

}
