package azmiu.library.service.concurate;

import azmiu.library.criteria.EmployeeCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.EmployeeRepository;
import azmiu.library.model.request.EmployeeRequest;
import azmiu.library.model.response.EmployeeResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.EmployeeService;
import azmiu.library.service.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static azmiu.library.mapper.EmployeeMapper.EMPLOYEE_MAPPER;

@Service
@RequiredArgsConstructor
public class EmployeeServiceHandler implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(UserEntity userEntity, EmployeeRequest request) {
        var employee = EMPLOYEE_MAPPER.buildEmployeeEntity(userEntity,request);
        employeeRepository.save(employee);
    }

    @Override
    public PageableResponse<EmployeeResponse> getAllEmployee(PageCriteria pageCriteria, EmployeeCriteria employeeCriteria) {
         var page =  employeeRepository.findAll(
                new EmployeeSpecification(employeeCriteria),
                PageRequest.of(pageCriteria.getPage(),pageCriteria.getCount(), Sort.by("id").ascending())
        );

         return EMPLOYEE_MAPPER.pageableEmployeeResponse(page);
    }


}
