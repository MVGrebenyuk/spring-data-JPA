package com.skillbox.spring.web.controllers;

import com.skillbox.spring.web.entities.Student;
import com.skillbox.spring.web.exceptions.ResourceNotFoundException;
import com.skillbox.spring.web.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found, id: " + id));
    }

    @GetMapping("/students/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/students/change_score")
    public void changeScore(@RequestParam Long studentId, @RequestParam Integer delta) {
        studentService.changeScore(studentId, delta);
    }

    @GetMapping("/students/score_between")
    public List<Student> findStudentsByScoreBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "100") Integer max) {
        return studentService.findByScoreBetween(min, max);
    }

    //Метод работы со спецификациями
    @GetMapping
    public Page<Student> getAllStudents(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_score", required = false) Integer minScore,
            @RequestParam(name = "max_score", required = false) Integer maxScore,
            @RequestParam(name = "name_part", required = false) String namePart
    ) {
        if (page < 1) {
            page = 1;
        }
        return studentService.find(minScore, maxScore, namePart, page);
    }
}
