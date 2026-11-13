package model;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class employee {
    private String id;
    private String name;
    private String department;
    private String designation;
    private String status;
    private int daysAbsent;

    public employee(String id, String name, String department, String designation, String status, int daysAbsent) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.status = status;
        this.daysAbsent = daysAbsent;
    }

    public abstract BigDecimal calculateGrossSalary();

    public BigDecimal calculateTax(BigDecimal gross) {
        return gross.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAttendancePenalty() {
        return new BigDecimal("50.00").multiply(BigDecimal.valueOf(daysAbsent)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateNetSalary() {
        BigDecimal gross = calculateGrossSalary();
        BigDecimal tax = calculateTax(gross);
        BigDecimal penalty = calculateAttendancePenalty();
        BigDecimal net = gross.subtract(tax).subtract(penalty);
        return net.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : net.setScale(2, RoundingMode.HALF_UP);
    }

    public abstract String toCSV();

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDesignation() { return designation; }
    public String getStatus() { return status; }
    public int getDaysAbsent() { return daysAbsent; }
}
