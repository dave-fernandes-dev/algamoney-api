package dev.fernandes.dave.algamoney.api.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import dev.fernandes.dave.algamoney.api.dto.ResumoLancamento;
import dev.fernandes.dave.algamoney.api.exceptions.ObjectnotFoundException;
import dev.fernandes.dave.algamoney.api.mail.Mailer;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.model.Usuario;
import dev.fernandes.dave.algamoney.api.repository.LancamentoRepository;
import dev.fernandes.dave.algamoney.api.repository.UsuarioRepository;
import dev.fernandes.dave.algamoney.api.repository.filters.LancamentoFilter;
import dev.fernandes.dave.algamoney.api.storage.S3;

@Service
public class LancamentoService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";  
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);	

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private S3 s3;

	public Lancamento findById(Integer id) {
		Optional<Lancamento> obj = lancamentoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	public List<Lancamento> filtrar(LancamentoFilter filter) {
		return lancamentoRepository.filtrar(filter);
	}
	
	public Page<Lancamento> filtrarPaginado(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.filtrarPaginado(filter, pageable);
	}
	
	public Page<ResumoLancamento> resumirPaginado(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.resumirPaginado(filter, pageable);
	}

	public Lancamento create(Lancamento objDTO) {
		objDTO.setId(0);
		
		if (StringUtils.hasText(objDTO.getAnexo())) {
			s3.salvar(objDTO.getAnexo());
		}
		
		if (pessoaService.isInativa(objDTO.getPessoa().getId())) {
			throw new DataIntegrityViolationException("Não pode Cadastrar Lançamento com pessoa Inativa");
		}
		
		objDTO.setUrlAnexo(s3.configurarUrl(objDTO.getAnexo()));
		
		return lancamentoRepository.save(objDTO);
	}

	public Lancamento update(Integer id, @Valid Lancamento objDTO) {
			
		objDTO.setId(id);
		Lancamento oldObj = findById(id);
		
		//se o DTO não tem anexo & o lancamento antigo tem anexo, então é para apagar o antigo
		if (!StringUtils.hasText(objDTO.getAnexo()) && StringUtils.hasText(oldObj.getAnexo()) )  {
			s3.remover(oldObj.getAnexo());	
			
		//se o anexo enviado é diferente do antigo, então substituir 
		} else if (StringUtils.hasText(objDTO.getAnexo()) && !objDTO.getAnexo().equals(oldObj.getAnexo()) ) {
			s3.substituir(oldObj.getAnexo(), objDTO.getAnexo());
		}
				
		// copia atributos de um para outro, ignorando "id"
		BeanUtils.copyProperties(objDTO, oldObj, "id");		
		
		oldObj.setUrlAnexo(s3.configurarUrl(oldObj.getAnexo()));
		
		return lancamentoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Lancamento obj = findById(id);
		lancamentoRepository.deleteById(obj.getId());
	}
	
	//@Scheduled(fixedDelay = 1000 * 60 * 30 )
	public void avisarSobreLancamentosVencidos() throws InterruptedException{
		//Thread.sleep(7000); //esperar 7 seg
		
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de "
					+ "e-mails de aviso de lançamentos vencidos.");
		}
		
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			
			return;
		}
		
		logger.info("Exitem {} lançamentos vencidos.", vencidos.size());
		
		List<Usuario> destinatarios = usuarioRepository
				.findByPermissoesDescricao(DESTINATARIOS);
		
		if (destinatarios.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas o "
					+ "sistema não encontrou destinatários.");
			
			return;
		}
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		
		logger.info("Envio de e-mail de aviso concluído."); 
	
		
		System.out.println(">>>>>>>>>> agendado FixedDelay:" + new Date());
	}
	
	//@Scheduled(cron = "40 33 11 * * * ")
	public void avisarSobreLancamentosVencidos2() throws InterruptedException{
		System.out.println(">>>>>>>>>> agendado Cron:" + new Date());
	}

	/*
	 * public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws
	 * Exception { List<LancamentoEstatisticaByPessoa> dados =
	 * lancamentoRepository.porPessoa(inicio, fim);
	 * 
	 * Map<String, Object> parametros = new HashMap<>(); parametros.put("DT_INICIO",
	 * Date.valueOf(inicio)); parametros.put("DT_FIM", Date.valueOf(fim));
	 * parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
	 * 
	 * InputStream inputStream = this.getClass().getResourceAsStream(
	 * "/relatorios/lancamentos-por-pessoa.jasper");
	 * 
	 * JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream,
	 * parametros, new JRBeanCollectionDataSource(dados));
	 * 
	 * return JasperExportManager.exportReportToPdf(jasperPrint); }
	 */

}
