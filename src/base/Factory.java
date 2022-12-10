package base;

import department.DepartmentManagement;
import employee.EmployeeManagement;
import onleave.OnleaveManagement;
import shift.ShiftManagement;
import timekeeping.TimeKeepingManagement;

//factory pattern
public class Factory {
    public static IAction getInstance(String type) {
        switch (type) {
            case "department":
                return new DepartmentManagement();
            case "employee":
                return new EmployeeManagement();
            case "shift":
                return new ShiftManagement();
            case "timekeeping":
                return new TimeKeepingManagement();
            case "onleave":
                return new OnleaveManagement();
            default:
                return null;
        }
    }

}

