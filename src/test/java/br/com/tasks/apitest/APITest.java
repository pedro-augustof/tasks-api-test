package br.com.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRestornarTarefas() {
        RestAssured.given()
                .when()
                    .get("/todo")
                .then()
                .statusCode(200)
                ;
    }

    @Test
    public void deveRetornarTarefaComSucesso() {
        RestAssured.given()
                    .body("{ \"task\": \"Teste via Api\", \"dueDate\": \"2023-12-30\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201)
        ;
    }

    @Test
    public void naodeveRetornarTarefaInvalida() {
        RestAssured.given()
                    .body("{ \"task\": \"Teste via Api\", \"dueDate\": \"2020-12-30\" }")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}