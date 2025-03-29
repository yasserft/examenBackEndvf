package com.example.examen.Service;

import com.example.examen.Entity.Department;
import com.example.examen.Repo.DepartmentRepo;
import com.example.examen.Repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepository;

    // Créer un nouveau département
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Récupérer tous les départements
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
