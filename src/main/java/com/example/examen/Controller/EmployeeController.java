package com.example.examen.Controller;

import com.example.examen.DTO.EmployeeRequestDTO;
import com.example.examen.Entity.Employee;
import com.example.examen.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/department/{id}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Long id) {
        return employeeService.findByDepartmentId(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> addEmployee(@ModelAttribute EmployeeRequestDTO employeeDTO) throws IOException{
        Employee employee = employeeService.saveEmployee(
                employeeDTO.getName(),
                employeeDTO.getAge(),
                employeeDTO.getDepartmentId(),
                employeeDTO.getPhoto()
        );
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) throws IOException {
        employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/age/{age}")
    public void deleteEmployeesOlderThan(@PathVariable int age) {
        employeeService.deleteEmployeesOlderThan(age);
    }
}
