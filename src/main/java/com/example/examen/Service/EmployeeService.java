package com.example.examen.Service;
import com.example.examen.Entity.Department;
import com.example.examen.Entity.Employee;
import com.example.examen.Repo.DepartmentRepo;
import com.example.examen.Repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private DepartmentRepo departmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 1. Récupérer les employés d'un département
    public List<Employee> findByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    // 2. Ajouter un employé avec photo
    public Employee saveEmployee(String name, int age, Long departmentId, MultipartFile photo) throws IOException {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé"));

        // Créer le répertoire s'il n'existe pas
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Sauvegarder la photo
        String photoName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        String photoPath = uploadDir + photoName;
        Files.copy(photo.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);


        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setDepartment(department);
        employee.setPhotoPath(photoPath);

        return employeeRepository.save(employee);
    }

    // 3. Supprimer un employé avec sa photo
    public void deleteEmployee(Long id) throws IOException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé"));

        // Supprimer la photo
        if (employee.getPhotoPath() != null) {
            Files.deleteIfExists(Paths.get(employee.getPhotoPath()));
        }

        employeeRepository.delete(employee);
    }

    // 4. Supprimer les employés de plus de X ans
    public void deleteEmployeesOlderThan(int age) {
        List<Employee> employees = employeeRepository.findByAgeGreaterThan(age);

        employees.forEach(employee -> {
            try {
                if (employee.getPhotoPath() != null) {
                    Files.deleteIfExists(Paths.get(employee.getPhotoPath()));
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la suppression de la photo", e);
            }
        });

        employeeRepository.deleteAll(employees);
    }

    // 5. Mettre à jour un employé
    public Employee updateEmployee(Long id, Employee employeeDetails, MultipartFile newPhoto) throws IOException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé non trouvé"));

        if (newPhoto != null && !newPhoto.isEmpty()) {
            // Supprimer l'ancienne photo
            if (employee.getPhotoPath() != null) {
                Files.deleteIfExists(Paths.get(employee.getPhotoPath()));
            }

            // Sauvegarder la nouvelle photo
            String photoName = UUID.randomUUID() + "_" + newPhoto.getOriginalFilename();
            String photoPath = uploadDir + photoName;
            Files.copy(newPhoto.getInputStream(), Paths.get(photoPath), StandardCopyOption.REPLACE_EXISTING);
            employee.setPhotoPath(photoPath);
        }

        employee.setName(employeeDetails.getName());
        employee.setAge(employeeDetails.getAge());

        if (employeeDetails.getDepartment() != null) {
            Department department = departmentRepository.findById(employeeDetails.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé"));
            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }
}