package dev.fernandes.dave.algamoney.api.repository.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.util.StringUtils;

import dev.fernandes.dave.algamoney.api.AlgamoneyApiApplication;
import dev.fernandes.dave.algamoney.api.model.Lancamento;
import dev.fernandes.dave.algamoney.api.storage.S3;

public class LancamentoAnexoListener {
	
	@PostLoad
	public void postLoad(Lancamento lancamento) {
		setUrlAnexo(lancamento);
	}

	private void setUrlAnexo(Lancamento lancamento) {
		if (StringUtils.hasText(lancamento.getAnexo())) {
			S3 s3 = AlgamoneyApiApplication.getBean(S3.class);
			lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
		}
	}
	
	@PostPersist
	public void postPersist(Lancamento lancamento) {
		setUrlAnexo(lancamento);
	}
	
	@PostUpdate
	public void postUpdate(Lancamento lancamento) {
		setUrlAnexo(lancamento);
	}
	
	

}