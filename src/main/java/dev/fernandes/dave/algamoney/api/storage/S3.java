package dev.fernandes.dave.algamoney.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.amazonaws.services.s3.model.Tag;

import dev.fernandes.dave.algamoney.api.config.property.AlgamoneyApiProperty;

@Component
//@Profile(value="prod & !prod & dev") // apenas exemplo de opcoes
//@Profile(value="default") // TODO este profile só é ativado quando não houver outro, então na prática fica desativado, é o q quero agora
public class S3 {
	
	private static final Logger logger = LoggerFactory.getLogger(S3.class);
	
	@Autowired
	private AlgamoneyApiProperty property;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public String salvarTemporariamente(MultipartFile arquivo) {
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());
		
		String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					property.getS3().getBucket(),
					nomeUnico,
					arquivo.getInputStream(), 
					objectMetadata)
					.withAccessControlList(acl);
			
			putObjectRequest.setTagging(new ObjectTagging(Arrays.asList(new Tag("expirar", "true"))));
			
			amazonS3.putObject(putObjectRequest);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Arquivo {} enviado com sucesso para o S3.", arquivo.getOriginalFilename());
			}
			
			return nomeUnico;
		} catch (IOException e) {
			throw new RuntimeException("Problemas ao tentar enviar o arquivo para o S3.", e);
		}
	}
	
	public String configurarUrl(String obj) {
		return "\\\\" + property.getS3().getBucket() + ".s3.amazonaws.com/" + obj;
	}

	private String gerarNomeUnico(String originalFilename) {
		return  UUID.randomUUID().toString() + "_" + originalFilename ;
	}

	public void salvar(String obj) {
		// atribuindo uma tag vazia, na prática diz q não precisa mais apagar após 1 dia 
		SetObjectTaggingRequest objectTaggingRequest = new SetObjectTaggingRequest(property.getS3().getBucket(), obj, new ObjectTagging(Collections.emptyList()));
		
		amazonS3.setObjectTagging(objectTaggingRequest);
	}

	public void remover(String obj) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(property.getS3().getBucket(), obj);
		
		amazonS3.deleteObject(deleteObjectRequest);
	}

	public void substituir(String antigo, String novo) {
		if (StringUtils.hasText(antigo) ) {
			this.remover(antigo);
		}
		
		//na pratica tira expiracao, pq, em tese, foi enviado antes
		salvar(novo);
	}

}
