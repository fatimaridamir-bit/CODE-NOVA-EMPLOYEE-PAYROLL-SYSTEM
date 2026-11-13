package ui;
import model.employee;
import model.FullTimeEmployee;
import model.HourlyEmployee;
import repository.EmployeeRepository;
import service.PayrollService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        PayrollService payrollService = new PayrollService(repository);

        if (payrollService.getAllEmployees().isEmpty()) {
            seedSampleData(payrollService);
        }

        while (true) {
            System.out.println("\n=== EMPLOYEE PAYROLL & ATTENDANCE SYSTEM ===");
            System.out.println("1. Add Full-Time Employee");
            System.out.println("2. Add Hourly Employee");
            System.out.println("3. View All Payroll Reports");
            System.out.println("4. Filter by Department");
            System.out.println("5. Filter by Designation");
            System.out.println("6. Filter by Employment Status");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addFullTimeEmployee(payrollService);
                    break;
                case "2":
                    addHourlyEmployee(payrollService);
                    break;
                case "3":
                    displayEmployees(payrollService.getAllEmployees());
                    break;
                case "4":
                    System.out.print("Enter Department: ");
                    displayEmployees(payrollService.filterByDepartment(scanner.nextLine().trim()));
                    break;
                case "5":
                    System.out.print("Enter Designation: ");
                    displayEmployees(payrollService.filterByDesignation(scanner.nextLine().trim()));
                    break;
                case "6":
                    System.out.print("Enter Status (Active / On Leave): ");
                    displayEmployees(payrollService.filterByStatus(scanner.nextLine().trim()));
                    break;
                case "7":
                    System.out.println("Exiting Payroll System. Records saved to employees.csv.");
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1-7.");
            }
        }
    }

    private static void seedSampleData(PayrollService service) {
        service.addEmployee(new FullTimeEmployee("EMP01", "Ayesha Khan", "Engineering", "Senior Developer", "Active", 1, new BigDecimal("5000.00"), new BigDecimal("500.00")));
        service.addEmployee(new FullTimeEmployee("EMP02", "Bilal Ahmed", "HR", "HR Manager", "Active", 0, new BigDecimal("4200.00"), new BigDecimal("300.00")));
        service.addEmployee(new HourlyEmployee("EMP03", "Sara Ali", "Support", "Technical Specialist", "Active", 2, new BigDecimal("25.00"), 160));
        service.addEmployee(new HourlyEmployee("EMP04", "Usman Tariq", "Operations", "Logistics Coordinator", "On Leave", 3, new BigDecimal("22.50"), 120));
    }

    private static void addFullTimeEmployee(PayrollService service) {
        try {
            System.out.print("Enter ID: ");
            String id = readNonEmptyString();
            System.out.print("Enter Name: ");
            String name = readNonEmptyString();
            System.out.print("Enter Department: ");
            String dept = readNonEmptyString();
            System.out.print("Enter Designation: ");
            String desig = readNonEmptyString();
            System.out.print("Enter Status (Active/On Leave): ");
            String status = readNonEmptyString();
            int absent = readNonNegativeInt("Enter Days Absent: ");
            BigDecimal base = readPositiveDecimal("Enter Monthly Base Salary: ");
            BigDecimal bonus = readNonNegativeDecimal("Enter Monthly Bonus: ");

            service.addEmployee(new FullTimeEmployee(id, name, dept, desig, status, absent, base, bonus));
            System.out.println("Full-time employee added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void addHourlyEmployee(PayrollService service) {
        try {
            System.out.print("Enter ID: ");
            String id = readNonEmptyString();
            System.out.print("Enter Name: ");
            String name = readNonEmptyString();
            System.out.print("Enter Department: ");
            String dept = readNonEmptyString();
            System.out.print("Enter Designation: ");
            String desig = readNonEmptyString();
            System.out.print("Enter Status (Active/On Leave): ");
            String status = readNonEmptyString();
            int absent = readNonNegativeInt("Enter Days Absent: ");
            BigDecimal rate = readPositiveDecimal("Enter Hourly Rate: ");
            int hours = readNonNegativeInt("Enter Hours Worked: ");

            service.addEmployee(new HourlyEmployee(id, name, dept, desig, status, absent, rate, hours));
            System.out.println("Hourly employee added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    private static void displayEmployees(List<employee> list) {
        if (list.isEmpty()) {
            System.out.println("No matching employee records found.");
            return;
        }
        System.out.println("\n-------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-8s %-15s %-13s %-21s %-10s %-10s %-10s %-10s %-10s%n",
                "ID", "Name", "Department", "Designation", "Status", "Gross($)", "Tax($)", "Penalty($)", "Net Pay($)");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        for (employee emp : list) {
            BigDecimal gross = emp.calculateGrossSalary();
            BigDecimal tax = emp.calculateTax(gross);
            BigDecimal penalty = emp.calculateAttendancePenalty();
            BigDecimal net = emp.calculateNetSalary();

            System.out.printf("%-8s %-15s %-13s %-21s %-10s %-10.2f %-10.2f %-10.2f %-10.2f%n",
                    emp.getId(), emp.getName(), emp.getDepartment(), emp.getDesignation(),
                    emp.getStatus(), gross, tax, penalty, net);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }

    private static String readNonEmptyString() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.print("Input cannot be empty. Try again: ");
        }
    }

    private static int readNonNegativeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= 0) return val;
                System.out.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    private static BigDecimal readPositiveDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                BigDecimal val = new BigDecimal(scanner.nextLine().trim());
                if (val.compareTo(BigDecimal.ZERO) > 0) return val;
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value.");
            }
        }
    }

    private static BigDecimal readNonNegativeDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                BigDecimal val = new BigDecimal(scanner.nextLine().trim());
                if (val.compareTo(BigDecimal.ZERO) >= 0) return val;
                System.out.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value.");
            }
        }
    }
}

