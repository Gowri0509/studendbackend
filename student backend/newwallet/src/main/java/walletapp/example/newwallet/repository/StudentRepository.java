package walletapp.example.newwallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import walletapp.example.newwallet.entity.Student;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,String>{
    Optional<Student> findByStudentNumber(String id);
    Optional<Student> findByName(String name);
}
