package com.todoapi;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.todoapi.model.Todo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    private int port = 8081;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/test",
                String.class)).contains("test controller");
    }
 
    @Test
    public void getCliente() {
    ResponseEntity<Todo> responseEntity = restTemplate.getForEntity("/api/customers/{id}", Todo.class, 1);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    
    Todo todo = responseEntity.getBody();
    assertThat(todo.getId()).isEqualTo(1);
    assertThat(todo.getTitle()).isEqualTo("Lorem ipsum");
}
}
