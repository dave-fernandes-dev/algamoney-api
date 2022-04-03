package dev.fernandes.dave.algamoney.api.exceptions;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Requisição Mal Formulada!", getRootCauseMessage(ex), request.getContextPath());

		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ObjectnotFoundException.class)
	public ResponseEntity<StandardError> objectnotFoundException(ObjectnotFoundException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Objeto Não Encontrado!", getRootCauseMessage(ex), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler( {EmptyResultDataAccessException.class} )
	public ResponseEntity<StandardError> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Objeto Não Encontrado!", getRootCauseMessage(ex), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Requisição Mal Formulada!", getRootCauseMessage(ex), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler({ org.springframework.dao.DataIntegrityViolationException.class} )
	public ResponseEntity<StandardError> handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Requisição Mal Formulada!", getRootCauseMessage(ex), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	//método muito útil para pegar a causa raiz da exception
	private String getRootCauseMessage(Exception ex) {
		return org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage(ex);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
				"Validation error", "Erro na validação dos campos", ((ServletWebRequest)request).getRequest().getRequestURI());
		
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}	

	
//	//@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
//
//		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), 
//				"Validation error", "Erro na validação dos campos", request.getRequestURI());
//		
//		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
//			errors.addError(x.getField(), x.getDefaultMessage());
//		}
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(),
				"Acesso Negado!", getRootCauseMessage(ex), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}
	
	//este exemplo retorna somente o notfound
	//@ExceptionHandler( {EmptyResultDataAccessException.class} )
	//@ResponseStatus(HttpStatus.NOT_FOUND)
	//public void handleEmptyResultDataAccessException() {}

}








