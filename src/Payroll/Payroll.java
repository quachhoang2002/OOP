package Payroll;

public class Payroll {
    private String id;
    private String employeeId;
    private String totalSalary;
    private String date;

    public Payroll() {
        this.id = "";
        this.employeeId = "";
        this.totalSalary = "";
        this.date = "";
    }

    public Payroll(String id, String employeeId, String totalSalary, String date) {
        this.id = id;
        this.employeeId = employeeId;
        this.totalSalary = totalSalary;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
