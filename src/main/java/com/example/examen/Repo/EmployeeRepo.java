package com.example.examen.Repo;

import com.example.examen.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByAgeGreaterThan(int age);

    List<Employee> findByNameContainingIgnoreCase(String name);

}
