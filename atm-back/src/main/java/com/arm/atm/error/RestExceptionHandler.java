package com.arm.atm.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.arm.atm.response.Response;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
				
		String mensagem = "Dados Inválidos.";
		List<Erro> erros = Arrays.asList(new Erro(mensagem));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST.UNPROCESSABLE_ENTITY, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, 
	                                                              HttpStatus status, WebRequest request) {
	    
		Response<Object> response = new Response<Object>();
		response=carregaResponse(ex,status,request);
		return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST.UNPROCESSABLE_ENTITY, request);
				
	}
	
	private Response<Object> carregaResponse(MethodArgumentNotValidException ex,HttpStatus status,WebRequest request){
		Response<Object> response = new Response<Object>();
		response.setErrors(ListaErros(ex));
		return response;
	}
	
	private List<String>ListaErros(MethodArgumentNotValidException ex){
		List<String> erros = new ArrayList<>();
		
		 String mensagem="";
		 for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			 mensagem=error.getField() + ": " + error.getDefaultMessage();
		 	 erros.add(mensagem);
		  }
		return erros;
	}	
		
	public static class Erro {
		private String mensagem;
		public Erro(String mensagem) {
			this.mensagem=mensagem;
		}
	}
}