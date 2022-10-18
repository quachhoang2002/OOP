import department.Department;

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



}
