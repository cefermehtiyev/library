package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.StudentCriteria;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.StudentResponse;
import azmiu.library.service.abstraction.StudentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public PageableResponse<StudentResponse> getAllStudents(PageCriteria pageCriteria, StudentCriteria studentCriteria){
        return studentService.getAllStudents(pageCriteria,studentCriteria);
    }
}
