package com.swissre.payroll.controller;

import com.swissre.payroll.model.Employee;
import com.swissre.payroll.service.PayrollService;

import java.util.Map;

public class PayrollController {

    PayrollService payrollService;

    public PayrollController() {
        this.payrollService = new PayrollService();
    }

    public void readEmployees() throws Exception {
        this.payrollService.readEmployees("src/main/resources/swissretest.csv");
    }

    public void generateReport() {
        Map<Employee, Double> salaryReportMap = this.payrollService.generateSalaryReport();
        for(Map.Entry<Employee, Double> entry : salaryReportMap.entrySet()) {
            Employee manager = entry.getKey();
            Double salaryDifference = entry.getValue();
            System.out.println("Manager " + manager.getFullName() + " earns Rs. " + Math.abs(salaryDifference) + (salaryDifference > 0 ? " more" : " less") + " than the average salary of his employees.");
        }

        Map<Employee, Integer> reportingLineMap = this.payrollService.generateReportingLineReport();
        for(Map.Entry<Employee, Integer> entry : reportingLineMap.entrySet()) {
            Employee employee = entry.getKey();
            System.out.println("Employee " + employee.getFullName() + " has a reporting line of " + entry.getValue() + " managers.");
        }
    }
}
