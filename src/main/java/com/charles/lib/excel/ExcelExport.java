package com.charles.lib.excel;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;
import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExcelExport {

    public static void main(String[] args) throws IOException {
        List<Employee> employees = Employee.generateSampleData();
        // System.out.println(employees);
        //InputStream is = new FileInputStream("/Users/Charles/Downloads/simple_export_template.xlsx");
        String test_1_path = ExcelExport.class.getClassLoader().getResource("employee_test_1.xlsx").getFile();
        String test_2_path = ExcelExport.class.getClassLoader().getResource("employee_test_2.xlsx").getFile();
        InputStream is_1 = new FileInputStream(test_1_path);
        InputStream is_2 = new FileInputStream(test_2_path);
        // InputStream is = new FileInputStream("/Users/Charles/Downloads/simple_export_template.xlsx");
        OutputStream os_1 = new FileOutputStream("/Users/Charles/Downloads/simple_export_template_1.xlsx");
        OutputStream os_2 = new FileOutputStream("/Users/Charles/Downloads/simple_export_template_2.xlsx");

        SimpleExporter exporter = new SimpleExporter();
        // List<String> headers1 = Arrays.asList("Name", "Birthday", "Payment");
        // exporter.gridExport(headers1, employees, "name, birthDate, payment", os);

        //exporter.registerGridTemplate(new FileInputStream());
        Context context = new Context();
        context.putVar("employees", employees);
        JxlsHelper.getInstance().processTemplateAtCell(is_1, os_1, context, "Result!A1");

        // now let's show how to register custom template
        exporter.registerGridTemplate(is_2);
        List<String> headers2 = Arrays.asList("Name", "BirthDate", "Payment", "Bonus");
        exporter.gridExport(headers2, employees, "name,birthDate,payment,bonus", os_2);

    }

    public static class Employee {
        private String name;
        private Date birthDate;
        private Double payment;
        private Double bonus;

        public Employee(String name, Date birthDate, Double payment, Double bonus) {
            this.name = name;
            this.birthDate = birthDate;
            this.payment = payment;
            this.bonus = bonus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
        }

        public Double getPayment() {
            return payment;
        }

        public void setPayment(Double payment) {
            this.payment = payment;
        }

        public Double getBonus() {
            return bonus;
        }

        public void setBonus(Double bonus) {
            this.bonus = bonus;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }

        public static List<Employee> generateSampleData() {
            final String DATE_PATTERN = "yyyy-MM-dd";
            Employee e1 = null, e2 = null, e3 = null, e4 = null, e5 = null;
            try {
                e1 = new Employee("Charles", DateUtils.parseDate("1991-10-01", DATE_PATTERN), 1929.23, 2311.32);
                e2 = new Employee("Lily", DateUtils.parseDate("1993-11-22", DATE_PATTERN), 1939.23, 2312.32);
                e3 = new Employee("John", DateUtils.parseDate("1988-06-23", DATE_PATTERN), 1949.23, 2313.32);
                e4 = new Employee("Lucy", DateUtils.parseDate("1994-12-15", DATE_PATTERN), 1959.23, 2314.32);
                e5 = new Employee("Amy", DateUtils.parseDate("1978-05-13", DATE_PATTERN), 1969.23, 2315.32);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Lists.newArrayList(e1, e2, e3, e4, e5);
        }
    }
}

