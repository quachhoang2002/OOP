import employee.Department;

import java.util.List;

public class Test {
    protected String name = "Test";
    protected static final Integer id = 1;
    public Test (String name) {
        System.out.println(name);
    }

    private Department department ;

    public Test(Department department) {
        this.department = department;
    }
    public List<Department> myFunc() {
        Test department = Test.this;
        this.department.readFile();
        List<Department> departmentList = this.department.getDepartmentList();
        return departmentList;
    }


}
