package com.zair.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Student {

    @Id
    private String id;

    private String name;

    private String email;

    private Integer age;
}
