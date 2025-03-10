package com.swissre.payroll.service;

import com.swissre.payroll.dao.PayrollDAO;
import com.swissre.payroll.model.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollService {

    PayrollDAO payrollDAO;

    public PayrollService() {
        this.payrollDAO = PayrollDAO.getPayrollDAO();
    }

    public void readEmployees(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        br.readLine();
        String line;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int id = Integer.parseInt(values[0]);
            int salary = Integer.parseInt(values[3]);
            Integer managerId = null;
            if(values.length == 5)
                managerId = Integer.valueOf(values[4]);
            Employee employee = new Employee(id, values[1], values[2], salary, managerId);
            this.payrollDAO.addEmployee(employee);
        }
    }

    public Map<Employee, Double> generateSalaryReport() {
        Map<Employee, Double> managerSalaryDifferenceMap = new HashMap<>();
        for(Integer managerId : this.payrollDAO.getAllManagerIds()) {
            Set<Employee> reports = this.payrollDAO.getReports(managerId);
            Employee manager = this.payrollDAO.getEmployee(managerId);
            int totalSalary = reports.stream().map(Employee::getSalary).mapToInt(Integer::intValue).sum();
            int averageSalary = totalSalary / reports.size();
            if(averageSalary * 1.2 > manager.getSalary())
                managerSalaryDifferenceMap.put(manager, manager.getSalary() - averageSalary * 1.2);
            if(averageSalary * 1.5 < manager.getSalary())
                managerSalaryDifferenceMap.put(manager, manager.getSalary() - averageSalary * 1.5);
        }

        return managerSalaryDifferenceMap;
    }

    public Map<Employee, Integer> generateReportingLineReport() {
        Map<Employee, Integer> employeeReportingLineMap = new HashMap<>();
        for(Integer employeeId : this.payrollDAO.getAllEmployeeIds()) {
            int reportingLine = 0;
            Integer currentEmployeeId = employeeId;
            while(currentEmployeeId != null) {
                Employee currentEmployee = this.payrollDAO.getEmployee(currentEmployeeId);
                if(currentEmployee.getManagerId() != null)
                    reportingLine++;
                currentEmployeeId = currentEmployee.getManagerId();
            }

            if(reportingLine > 4) {
                Employee employee = this.payrollDAO.getEmployee(employeeId);
                employeeReportingLineMap.put(employee, reportingLine);
            }
        }

        return employeeReportingLineMap;
    }

}
