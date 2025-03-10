package com.swissre.payroll.dao;

import com.swissre.payroll.model.Employee;

import java.util.*;

public class PayrollDAO {

    private static PayrollDAO payrollDAO;

    private Map<Integer, Employee> employeeMap;

    private Map<Integer, Set<Employee>> managerMap;

    private PayrollDAO() {
        super();
        this.employeeMap = new HashMap<>();
        this.managerMap = new HashMap<>();
    }

    public static PayrollDAO getPayrollDAO() {
        if(payrollDAO == null)
            payrollDAO = new PayrollDAO();
        return payrollDAO;
    }

    public void addEmployee(Employee employee) {
        this.employeeMap.put(employee.getId(), employee);
        if(employee.getManagerId() != null) {
            managerMap.computeIfAbsent(employee.getManagerId(), k -> new HashSet<>());
            managerMap.get(employee.getManagerId()).add(employee);
        }
    }

    public Set<Integer> getAllManagerIds() {
        return this.managerMap.keySet();
    }

    public Set<Employee> getReports(int managerId) {
        return this.managerMap.get(managerId);
    }

    public Employee getEmployee(int employeeId) {
        return this.employeeMap.get(employeeId);
    }

    public Set<Integer> getAllEmployeeIds() {
        return this.employeeMap.keySet();
    }

}
