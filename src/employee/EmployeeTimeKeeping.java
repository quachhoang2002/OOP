package employee;

public class EmployeeTimeKeeping extends Employee{
    private String id;
    private String employeeId;
    private String timeWorked;

    public EmployeeTimeKeeping() {
        this.id = "";
        this.employeeId = "";
        this.timeWorked = "";
    }

    public EmployeeTimeKeeping(String id, String employeeId, String timeWorked) {
        this.id = id;
        this.employeeId = employeeId;
        this.timeWorked = timeWorked;
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

    public String getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(String timeWorked) {
        this.timeWorked = timeWorked;
    }
}
