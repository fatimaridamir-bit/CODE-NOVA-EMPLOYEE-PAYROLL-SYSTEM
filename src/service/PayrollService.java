package service;
import model.employee;
import repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PayrollService {
    private List<employee> employees;
    private EmployeeRepository repository;

    public PayrollService(EmployeeRepository repository) {
        this.repository = repository;
        this.employees = repository.loadEmployees();
    }

    public void addEmployee(employee employee) {
        employees.add(employee);
        repository.saveEmployees(employees);
    }

    public List<employee> getAllEmployees() {
        return employees;
    }

    public List<employee> filterByDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<employee> filterByDesignation(String designation) {
        return employees.stream()
                .filter(e -> e.getDesignation().equalsIgnoreCase(designation))
                .collect(Collectors.toList());
    }

    public List<employee> filterByStatus(String status) {
        return employees.stream()
                .filter(e -> e.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}

