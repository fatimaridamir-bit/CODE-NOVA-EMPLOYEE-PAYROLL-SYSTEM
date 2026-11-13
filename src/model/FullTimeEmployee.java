package model;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class FullTimeEmployee extends employee {
    private BigDecimal monthlyBaseSalary;
    private BigDecimal bonus;

    public FullTimeEmployee(String id, String name, String department, String designation, String status, int daysAbsent, BigDecimal monthlyBaseSalary, BigDecimal bonus) {
        super(id, name, department, designation, status, daysAbsent);
        this.monthlyBaseSalary = monthlyBaseSalary;
        this.bonus = bonus;
    }

    @Override
    public BigDecimal calculateGrossSalary() {
        return monthlyBaseSalary.add(bonus).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toCSV() {
        return String.join(",", "FULL_TIME", getId(), getName(), getDepartment(), getDesignation(), getStatus(),
                String.valueOf(getDaysAbsent()), monthlyBaseSalary.toString(), bonus.toString());
    }
}
