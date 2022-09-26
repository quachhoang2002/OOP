package shift;

public interface IPayroll {
    public String getId();
    public void setId(String id);
    public String getEmployeeId();
    public void setEmployeeId(String employeeId);
    public String getTimeWorked();
    public void setTimeWorked(String timeWorked);

    public Payroll findById(String id);
    public List<Payroll> getPayrollList();
}
