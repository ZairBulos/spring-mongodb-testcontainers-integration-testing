package com.zair.repositories;

import com.zair.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    @Query("{name:  ?0}")
    Optional<Student> findByName(String name);

    @Query("{age:  { $gte: ?0 } }")
    List<Student> findByAgeGreaterThan(Integer age);

    List<Student> findByAgeLessThan(Integer age);
}
