package department;

import base.BaseInterface;
import department.Department;

import java.util.List;

public interface IDepartmenManagement extends BaseInterface {
    //implement method
    public List<Department> getDepartmentList();
    public Department findById(String id);
    public Department findByName(String name);
}
