package com.example.examen.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class logger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.example.examen.Service.EmployeeService.saveEmployee(..))")
    public void logBeforeAddEmployee(JoinPoint joinPoint) {
        logger.info("Adding new employee: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* com.example.examen.Service.EmployeeService.saveEmployee(..))")
    public void logAfterAddEmployee(JoinPoint joinPoint) {
        logger.info("Employee added successfully");
    }

    @Before("execution(* com.example.examen.Service.EmployeeService.updateEmployee(..))")
    public void logBeforeUpdateEmployee(JoinPoint joinPoint) {
        logger.info("Updating employee: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* com.example.examen.Service.EmployeeService.updateEmployee(..))")
    public void logAfterUpdateEmployee(JoinPoint joinPoint) {
        logger.info("Employee updated successfully");
    }

    @Before("execution(* com.example.examen.Service.EmployeeService.deleteEmployee(..))")
    public void logBeforeDeleteEmployee(JoinPoint joinPoint) {
        logger.info("Deleting employee with ID: {}", joinPoint.getArgs()[0]);
    }

    @After("execution(* com.example.examen.Service.EmployeeService.deleteEmployee(..))")
    public void logAfterDeleteEmployee(JoinPoint joinPoint) {
        logger.info("Employee deleted successfully");
    }
}
