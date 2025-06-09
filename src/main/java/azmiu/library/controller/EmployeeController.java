package azmiu.library.controller;

import azmiu.library.criteria.EmployeeCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.response.EmployeeResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    @GetMapping
    public PageableResponse<EmployeeResponse> getAllEmployee(PageCriteria pageCriteria, EmployeeCriteria employeeCriteria){
        return employeeService.getAllEmployee(pageCriteria,employeeCriteria);
    }

}
