package employee;

import base.BaseInterface;

import java.util.List;

public interface IDeparment extends BaseInterface {
    public String getId();
    public void setId(String id);
    public String getName();
    public void setName(String name);
    public String getQuantity();
    public void setQuantity(String quantity);
    public Department findById(String id);
    public List<Department> getDepartmentList();
}
