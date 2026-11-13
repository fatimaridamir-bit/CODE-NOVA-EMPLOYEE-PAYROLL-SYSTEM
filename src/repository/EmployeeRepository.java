package repository;

import model.employee;
import model.FullTimeEmployee;
import model.HourlyEmployee;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    private static final String FILE_PATH = "employees.csv";

    public void saveEmployees(List<employee> employees) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            writer.write("Type,ID,Name,Department,Designation,Status,DaysAbsent,RateOrBase,HoursOrBonus");
            writer.newLine();
            for (employee emp : employees) {
                writer.write(emp.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving employee records: " + e.getMessage());
        }
    }

    public List<employee> loadEmployees() {
        List<employee> list = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return list;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                String dept = parts[3];
                String desig = parts[4];
                String status = parts[5];
                int daysAbsent = Integer.parseInt(parts[6]);

                if ("FULL_TIME".equalsIgnoreCase(type)) {
                    BigDecimal base = new BigDecimal(parts[7]);
                    BigDecimal bonus = new BigDecimal(parts[8]);
                    list.add(new FullTimeEmployee(id, name, dept, desig, status, daysAbsent, base, bonus));
                } else if ("HOURLY".equalsIgnoreCase(type)) {
                    BigDecimal rate = new BigDecimal(parts[7]);
                    int hours = Integer.parseInt(parts[8]);
                    list.add(new HourlyEmployee(id, name, dept, desig, status, daysAbsent, rate, hours));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading employee records: " + e.getMessage());
        }
        return list;
    }
}

