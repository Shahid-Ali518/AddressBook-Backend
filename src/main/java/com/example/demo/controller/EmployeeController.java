package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.services.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("employees")
    public ResponseEntity<?> getEmployees(){
        List<Employee> employees = employeeService.getEmployees();
        if(employees != null && !employees.isEmpty()){

            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Record Fond!!", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<?> AddEmployee(@RequestBody Employee employee){
        try{
            employeeService.AddEmployee(employee);
            return new ResponseEntity<>("Employee Saved Successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

        }
    }

}
