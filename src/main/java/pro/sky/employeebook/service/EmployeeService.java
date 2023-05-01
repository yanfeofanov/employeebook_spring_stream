package pro.sky.employeebook.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.employeebook.Exception.ExceptionNotFound;
import pro.sky.employeebook.Exception.InvalidInputException;
import pro.sky.employeebook.models.Employee;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    public Employee addEmployee(Employee employee) {
        if(!validateInput(employee.getFirstName(),employee.getLastName()))
            throw new InvalidInputException();
        employees.add(employee);
        return employee;
    }


    public Employee findMaxSalaryFromDepartment(int department) throws ExceptionNotFound {
        return employees.stream()
                .filter(emp -> emp.getDepartment() == department)       // Фильтр по отделам
                .max(Comparator.comparing(Employee::getSalary)) // Фильтр по максимальному значение getSalary
                .orElseThrow(() -> new ExceptionNotFound(" Сотрудник с максимальной зарплатой не найден ")); // Выброс исключения
    }

    public Employee findMinSalaryFromDepartment(int department) throws ExceptionNotFound {
        return employees.stream()
                .filter(emp -> emp.getDepartment() == department)
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new ExceptionNotFound(" Сотрудник с минимальной зарплатой не найден "));
    }

    public List<Employee> findEmployeeFromDepartment(int department) {
        return employees.stream()
                .filter(emp -> emp.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public List<Employee> findAllEmployeesFromDepartment() {
        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::getDepartment))
                .collect(Collectors.toList());
    }
    private boolean validateInput(String firstName, String lastName){
        return (isAlpha(firstName) && isAlpha(lastName));
    }

}
