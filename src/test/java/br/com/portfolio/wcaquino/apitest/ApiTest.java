package br.com.portfolio.wcaquino.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured
			.given()
				.log().all()
			.when()
				.get("/todo")
			.then()
				.statusCode(200)
				.log().all()
			;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured
			.given()
				.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\" }")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.statusCode(201)
				.log().all()
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
			.given()
				.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
				.contentType(ContentType.JSON)
			.when()
				.post("/todo")
			.then()
				.log().all()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	
}
