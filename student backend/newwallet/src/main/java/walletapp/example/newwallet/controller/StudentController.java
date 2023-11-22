package walletapp.example.newwallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import walletapp.example.newwallet.entity.Student;
import walletapp.example.newwallet.repository.StudenttRepository;
import walletapp.example.newwallet.service.StudentServiceImpl;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    //    @ResponseStatus(value = HttpStatus.CREATED)

    @PostMapping("/addstudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentServiceImpl.addstudent(student));
    }

    @GetMapping("/allstudents")
    public ResponseEntity<List<Student>> getStudent(){
        return ResponseEntity.ok(studentServiceImpl.findAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id){
        return ResponseEntity.ok(studentServiceImpl.getStudentById(id));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteStudent(PathVariable String name)
    {
        return ResponseEntity.ok(studentServiceImpl.deleteStudent(name));
    }

    @PutMapping("/update-score/{name}/{newScore}")
    public ResponseEntity<String> updateScore(@PathVariable String name, @PathVariable int newScore) {
        for (Student result : satResults) {
            if (result.getName().equals(name)) {
                result.setSatScore(newScore);
                result.setPassed(newScore > 30);
                return ResponseEntity.ok("Score updated successfully");
            }
        }
        return ResponseEntity.notFound().build();
    }
}

