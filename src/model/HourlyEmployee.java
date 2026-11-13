
package model;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class HourlyEmployee extends employee {
    private BigDecimal hourlyRate;
    private int hoursWorked;

    public HourlyEmployee(String id, String name, String department, String designation, String status, int daysAbsent, BigDecimal hourlyRate, int hoursWorked) {
        super(id, name, department, designation, status, daysAbsent);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public BigDecimal calculateGrossSalary() {
        return hourlyRate.multiply(BigDecimal.valueOf(hoursWorked)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toCSV() {
        return String.join(",", "HOURLY", getId(), getName(), getDepartment(), getDesignation(), getStatus(),
                String.valueOf(getDaysAbsent()), hourlyRate.toString(), String.valueOf(hoursWorked));
    }
}
