package base;

import Payroll.PayrollManagement;
import department.DepartmentManagement;
import employee.EmployeeManagement;
import onleave.OnleaveManagement;
import shift.ShiftManagement;
import statistical.Statistical;
import timekeeping.TimeKeepingManagement;

//factory pattern
public class Factory {
    public ISystem getInstance(String type) {
        return switch (type) {
            case "department" -> new DepartmentManagement();
            case "employee" -> new EmployeeManagement();
            case "shift" -> new ShiftManagement();
            case "timekeeping" -> new TimeKeepingManagement();
            case "onleave" -> new OnleaveManagement();
            case "payroll" -> new PayrollManagement();
            default -> null;
        };
    }

}

