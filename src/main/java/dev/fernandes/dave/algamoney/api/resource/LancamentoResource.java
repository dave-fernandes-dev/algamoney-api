package dev.fernandes.dave.algamoney.api.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.dto.Anexo;
import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByCategoria;
import dev.fernandes.dave.algamoney.api.dto.LancamentoEstatisticaByDia;
import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.model.enums.MES;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;
import dev.fernandes.dave.algamoney.api.service.LancamentoService;
//import dev.fernandes.dave.algamoney.api.storage.S3;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService service;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	//@Autowired
	//private S3 s3;

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
		return this.lancamentoRepository.byCategoria(LocalDate.now().withMonth(MES.JANEIRO));
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

	//@PostMapping("/anexo-local")
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO')and hasAuthority('SCOPE_write')")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public String uploadAnexo(@RequestParam MultipartFile anexo) throws IOException {
		String folder = "/home/dave/Downloads/temp/anexo--";
		
		OutputStream out = new FileOutputStream(folder + anexo.getOriginalFilename());
		out.write(anexo.getBytes());
		out.close();
		
		return "ok";
	}
	
	@PostMapping("/anexo")
	@PreAuthorize("hasAnyRole('CADASTRAR_LANCAMENTO')and hasAuthority('SCOPE_write')")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public Anexo uploadAnexoS3(@RequestParam MultipartFile anexo) throws IOException {

		//String nome = s3.salvarTemporariamente(anexo);
		//return new Anexo(nome, s3.configurarUrl(nome));
		return null;
	}
	/*
	 * @GetMapping("/relatorios/por-pessoa")
	 * 
	 * @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')"
	 * ) public ResponseEntity<byte[]> relatorioPorPessoa(
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) throws
	 * Exception { byte[] relatorio = lancamentoService.relatorioPorPessoa(inicio,
	 * fim);
	 * 
	 * return ResponseEntity.ok() .header(HttpHeaders.CONTENT_TYPE,
	 * MediaType.APPLICATION_PDF_VALUE) .body(relatorio); }
	 */
}
