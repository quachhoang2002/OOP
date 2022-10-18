package employee;

import base.BaseInterface;

import java.util.List;

public interface IEmployeeManagement extends BaseInterface {
     //find by id
    public Employee findById(String id);
    public List<Employee> findByName(String name);
    public List<Employee> findByDepartmentId(String departmentId);
    public List<Employee> findByPermission(String permission);
}
