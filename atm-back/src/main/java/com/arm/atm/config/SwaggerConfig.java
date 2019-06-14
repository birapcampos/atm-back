package com.arm.atm.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final ResponseMessage m201 = messageCriacaoRecurso();
	private final ResponseMessage m204put = simpleMessage(204, "Atualização realizada com sucesso");
	private final ResponseMessage m204del = simpleMessage(204, "Deleção realizada com sucesso");
	private final ResponseMessage m400 = simpleMessage(400, "");
	private final ResponseMessage m403 = simpleMessage(403, "Não autorizado");
	private final ResponseMessage m404 = simpleMessage(404, "Não encontrado");
	private final ResponseMessage m409 = simpleMessage(409, "Erro de integridade");
	private final ResponseMessage m422 = simpleMessage(422, "Erro de validação");
	private final ResponseMessage m500 = simpleMessage(500, "Erro inesperado");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m403, m409, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m400, m403, m404, m409, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500)).select()
				.apis(RequestHandlerSelectors.basePackage("com.arm.atm.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API Atm Back")
				.description("API para executar operações de conta corrente")
				.termsOfServiceUrl("http://springfox.io")
				.contact(new Contact("GSW", "", "birapcampos@gmail.com"))
				.license("Apache License Version 2.0").licenseUrl("sistemas").version("1.0").build();
	}

	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}

	private ResponseMessage messageCriacaoRecurso() {
		Map<String, Header> map = new HashMap<>();
		map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
		return new ResponseMessageBuilder().code(201).message("Recurso criado").headersWithDescription(map).build();
	}
	

}