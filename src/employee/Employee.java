package employee;

import base.BaseService;
public class Employee extends BaseService  {
    // Properties
    private String id;
    private String name;
    private String age;
    private String gender;
    private String phone;
    private String email;
    private String salary;
    private String departmentId;
    private String permission;


    // Constructor
    public Employee() {
        this.id = "";
        this.name = "";
        this.age = "";
        this.gender = "";
        this.phone = "";
        this.email = "";
        this.salary = "";
        this.departmentId = "";
        this.permission = "";
    }

    public Employee(String id, String name, String age, String gender, String phone, String email, String salary, String departmentId, String permission) {
        //constructor
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
        this.permission = permission;
    }

    //region Getter and Setter
    //implement method
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    //endregion

    //base method
    //show menu


}
