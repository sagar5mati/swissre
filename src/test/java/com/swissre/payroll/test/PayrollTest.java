package com.swissre.payroll.test;

import com.swissre.payroll.controller.PayrollController;
import org.junit.Test;

public class PayrollTest {

    @Test
    public void testPayroll() throws Exception {
        try {
            PayrollController payrollController = new PayrollController();
            payrollController.readEmployees();
            payrollController.generateReport();
        } catch(Exception e) {
            throw e;
        }
    }
}
