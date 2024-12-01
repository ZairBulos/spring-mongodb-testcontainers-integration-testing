package com.zair;

import com.zair.entities.Student;
import com.zair.repositories.StudentRepository;
import com.zair.services.StudentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class StudentApplicationIntegrationTests {

    @Autowired
    private StudentService service;

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @BeforeAll
    static void setUp(@Autowired StudentRepository repository) {
        List<Student> students = Arrays.asList(
                new Student("1", "John", "john@mail.com", 19),
                new Student("2", "Jane", "jane@mail.com", 21),
                new Student("3", "Bruce", "bruce@mail.com", 29),
                new Student("4", "Martha", "martha@mail.com", 24)
        );

        repository.saveAll(students);
    }

    @Test
    public void findByName_ReturnsTheStudent() {
        final String name = "John";

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port + "/api/v1/students/{name}";

        ResponseEntity<Student> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Student>(){},
                name
        );
        Student student = response.getBody();

        assertThat(student).isNotNull();
        assertThat(student.getId()).isEqualTo("1");
        assertThat(student.getName()).isEqualTo("John");
        assertThat(student.getEmail()).isEqualTo("john@mail.com");
        assertThat(student.getAge()).isEqualTo(19);
    }

    @Test
    public void findByAgeGreaterThan_ReturnsTheListStudents() {
        final Integer age = 20;

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port + "/api/v1/students/agt/{age}";

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){},
                age
        );
        List<Student> students = response.getBody();

        List<String> ids = students.stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(3);
        assertThat(ids).hasSameElementsAs(Arrays.asList("2", "3", "4"));
    }

    @Test
    public void findByAgeLessThan_ReturnsTheListStudents() {
        final Integer age = 25;

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:" + port + "/api/v1/students/alt/{age}";

        ResponseEntity<List<Student>> response = restTemplate.exchange(
                resourceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){},
                age
        );
        List<Student> students = response.getBody();

        List<String> ids = students.stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());

        assertThat(students.size()).isEqualTo(3);
        assertThat(ids).hasSameElementsAs(Arrays.asList("1", "2", "4"));
    }
}
