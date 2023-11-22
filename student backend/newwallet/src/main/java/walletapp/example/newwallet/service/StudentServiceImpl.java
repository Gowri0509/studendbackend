 package walletapp.example.newwallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import walletapp.example.newwallet.entity.Students
import walletapp.example.newwallet.repository.Studentsepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    StudentService studentService;
    @Override
    public Student addStudent(Student wall){
        wall.setId(UUID.randomUUID().toString().split("-")[0]);
        return StudentRepository.save(wall);
    }
    @Override
    public List<Student> findAllStudents(){
        return  studentRepository.findAll();
    }
    @Override
    public Student getStudentById(String id){
        return studentRepository.findById(id).get();
    }

    @Override
    public Student modifyStudent(String id, Student student){
        Optional<Student> optional = studentRepository.findById(id);
        student.setsatscore(student.getsatscore());
        return studentRepository.save(student);
    }
    @Override
    public String deleteStudent(String id){
        studentRepository.deleteById(id);
        return " Student deleted";
    }

}
