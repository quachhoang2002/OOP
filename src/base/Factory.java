package base;

import department.DepartmentManagement;
import employee.EmployeeManagement;
import onleave.OnleaveManagement;
import timekeeping.TimeKeepingManagement;

//factory pattern
public class Factory {
    public static BaseInterface getInstance(String type) {
        if (type.equals("employee")) {
            return new EmployeeManagement();
        } else if (type.equals("department")) {
            return new DepartmentManagement();
        } else if (type.equals("TimeKeeping")) {
            return new TimeKeepingManagement();
        } else if (type.equals("onleave")) {
            return new OnleaveManagement();
        }
        return null;
    }

}

