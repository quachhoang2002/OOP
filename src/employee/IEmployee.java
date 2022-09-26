package employee;

import base.BaseInterface;


public interface IEmployee extends BaseInterface {
    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getAge();

    public void setAge(String age);

    public String getGender();

    public void setGender(String gender);

    public String getPhone();

    public void setPhone(String phone);

    public String getEmail();

    public void setEmail(String email);

    public String getSalary();

    public void setSalary(String salary);

    public String getDepartmentId();

    public void setDepartmentId(String departmentId);

    public String getPermission();

    public void setPermission(String permission);

    public Employee findById(String id);

}
