package com.example.examen.Controller;

import com.example.examen.Entity.Department;
import com.example.examen.Repo.DepartmentRepo;
import com.example.examen.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentRepo departmentRepository;

    // 1. Ajouter un nouveau département
    @PostMapping
    public ResponseEntity<Department> addNewDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.createDepartment(department);
        return ResponseEntity.ok(savedDepartment);
    }
    @GetMapping
    public List<String> getAllDepartments() {

        List<Department> department = departmentRepository.findAll();
        List<String> dep=new ArrayList<>();
        department.stream().map(depp->dep.add(depp.getName())).collect(Collectors.toList());
        return dep;
    }

}
