package walletapp.example.newwallet.service;

import walletapp.example.newwallet.entity.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student wall);
    List<Student> findAllStudents();
    Student getStudentById(String id);
    Student modifyStudent(String id, Student Student);
    String deleteStudent(String id);
}
