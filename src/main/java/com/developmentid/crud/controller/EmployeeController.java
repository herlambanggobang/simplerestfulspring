package com.developmentid.crud.controller;

import com.developmentid.crud.dto.EmployeeDto;
import com.developmentid.crud.dto.ResponseDto;
import com.developmentid.crud.entity.Employee;
import com.developmentid.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.developmentid.crud.service.impl.EmployeeServiceImpl;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/helloworld")
    public ResponseEntity<String> hello() {

        return new ResponseEntity<>("Yoii", HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> postEmployee(@RequestBody EmployeeDto employeeDto) {

        ResponseDto res = new ResponseDto();

        if(employeeService.getEmployeeByUserName(employeeDto.getUsername()) != null) {

            res.setCode(409);
            res.setStatus("CONFLICT");
            res.setMessages("Username already used!");
            res.setDatas(employeeDto);

            return new ResponseEntity<>(res, HttpStatus.CONFLICT);

        }

        res.setCode(200);
        res.setStatus("SUCCESS");
        res.setMessages("Success created employee!");
        res.setDatas(employeeService.saveEmployee(employeeDto));

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<?> getEmployees() {

        ResponseDto res = new ResponseDto();
        res.setCode(200);
        res.setStatus("SUCCESS");

        if(employeeService.getEmployee().size() < 1) {

            res.setMessages("Empty employees data!");

            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        res.setMessages("Success get employees data");
        res.setDatas(employeeService.getEmployee());

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {

        ResponseDto res = new ResponseDto();

        if(employeeService.getEmployeeById(id) == null) {
            res.setCode(404);
            res.setStatus("NOT FOUND");
            res.setMessages("Employee with ID " + id + " not found!");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        }

        res.setCode(200);
        res.setStatus("SUCCESS");
        res.setMessages("Success get data employee!");
        res.setDatas(employeeService.getEmployeeById(id));

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeById(@RequestBody EmployeeDto employeeDto, @PathVariable Long id) {

        ResponseDto res = new ResponseDto();

        if(employeeService.getEmployeeById(id) == null) {

            res.setCode(404);
            res.setStatus("NOT FOUND");
            res.setMessages("Employee with ID " + id + " Not Found!");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        res.setCode(200);
        res.setStatus("SUCCESS");
        res.setMessages("Success update Employee with ID " + id + "!");
        res.setDatas(employeeService.updateEmployee(id, employeeDto));

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

        ResponseDto res = new ResponseDto();

        if(employeeService.getEmployeeById(id) == null) {

            res.setCode(404);
            res.setStatus("NOT FOUND");
            res.setMessages("Employee with ID " + id + " Not Found!");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        }

       res.setCode(200);
       res.setStatus("SUCCESS");
       res.setMessages("Success delete employee");
       return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/getbyusername/{username}")
    public ResponseEntity<?> findEmployeeByUsername(@PathVariable String username) {

        EmployeeDto employeeExisting = employeeService.getEmployeeByUserName(username);

        ResponseDto res = new ResponseDto();

        if(employeeExisting == null) {

            res.setCode(404);
            res.setStatus("NOT FOUND");
            res.setMessages("Employee with username " + username + " is not exist!");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        }

        res.setCode(200);
        res.setStatus("SUCCESS");
        res.setMessages("Success get employee with username");
        res.setDatas(employeeExisting);

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
