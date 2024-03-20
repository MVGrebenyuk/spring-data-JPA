package com.skillbox.spring.web.services;

import com.skillbox.spring.web.entities.Student;
import com.skillbox.spring.web.exceptions.ResourceNotFoundException;
import com.skillbox.spring.web.repositories.StudentRepository;
import com.skillbox.spring.web.repositories.specifications.StudentsSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    //Поиск с использованием спецификаций
    public Page<Student> find(Integer minScore, Integer maxScore, String partName, Integer page) {
        //создаём дефолтную спецификацию
        Specification<Student> spec = Specification.where(null);
        //обогащаем в зависимости от параметров, что нам пришли
        if (minScore != null) {
            spec = spec.and(StudentsSpecifications.scoreGreaterOrEqualsThan(minScore));
        }
        if (maxScore != null) {
            spec = spec.and(StudentsSpecifications.scoreLessThanOrEqualsThan(maxScore));
        }
        if (partName != null) {
            spec = spec.and(StudentsSpecifications.nameLike(partName));
        }

        //выполняем поиск со спецификацией + пагинацией
        return studentRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }
    @Transactional
    public void changeScore(Long studentId, Integer delta) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Unable to change student's score. Student not found, id: " + studentId));
        student.setScore(student.getScore() + delta);
    }

    public List<Student> findByScoreBetween(Integer min, Integer max) {
        return studentRepository.findAllByScoreBetween(min, max);
    }
}
