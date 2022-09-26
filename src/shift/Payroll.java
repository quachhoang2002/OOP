package shift;

public class Payroll {
    private String id;
    private String employeeId;
    private String shiftId;
    private String date;
    private String checkIn;
    private String checkOut;
    private String reason;
    private String salary;
    private String netSalary;

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

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(String netSalary) {
        this.netSalary = netSalary;
    }

    public Payroll findById(String id) {
        return null;
    }

    public List<Payroll> getPayrollList() {
        return null;
    }
}
