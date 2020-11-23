package com.developmentid.crud.controller;

import com.developmentid.crud.entity.Employee;
import com.developmentid.crud.repository.EmployeeRepository;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;

    }

    @GetMapping("/helloworld")
    public ResponseEntity<String> hello() {

        return new ResponseEntity<>("Yoii", HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> postEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<?> getEmployees() {

        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if(employee == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeById(@RequestBody Employee employee, @PathVariable Long id) {

        Employee employeeExisting = employeeRepository.findById(id).orElse(null);

        if(employeeExisting == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        employee.setId(id);
        employeeRepository.save(employee);

        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

        Employee employeeExisting = employeeRepository.findById(id).orElse(null);

        if(employeeExisting == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        employeeRepository.deleteById(id);
        return new ResponseEntity<>(employeeExisting, HttpStatus.OK);

    }

}
