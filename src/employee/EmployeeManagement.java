package employee;

import base.SystemService;
import department.Department;
import department.DepartmentManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement extends SystemService {
    private static final String FILE_PATH = "employee.txt";
    protected static List<Employee> employeeList = new ArrayList<>();

    protected DepartmentManagement departmentManagement;

    //constructor
    public EmployeeManagement() {
        if (employeeList.isEmpty()) {
            readFile();
        }
        this.departmentManagement = new DepartmentManagement();
    }


    //region base method
    //find employee by id

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public Employee findById(String id) {
        for (Employee employee : EmployeeManagement.employeeList) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    //find employee by name
    public List<Employee> findByName(String name) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : EmployeeManagement.employeeList) {
            if (employee.getName().equals(name)) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    //find employee by department
    public List<Employee> findByDepartmentId(String departmentId) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : EmployeeManagement.employeeList) {
            if (employee.getDepartmentId().equals(departmentId)) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    //find employee by permission
    public List<Employee> findByPermission(String permission) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : EmployeeManagement.employeeList) {
            if (employee.getPermission().equals(permission)) {
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
    //endregion

    //region implement base method
    public void showMenu() {
        String select;
        do {
            System.out.println("||================== Menu ===================||");
            System.out.println("|| 1. Them Nhan Vien Moi                     ||");
            System.out.println("|| 2. Danh Sach Nhan Vien                    ||");
            System.out.println("|| 3. Sua Thong Tin Nhan Vien                ||");
            System.out.println("|| 4. Xoa Thong Tin Nhan Vien                ||");
            System.out.println("|| 5. Tim Nhan Vien                          ||");
            System.out.println("|| 0. Exit                                   ||");
            System.out.println("||===========================================||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    add();
                    break;
                case "2":
                    show();
                    break;
                case "3":
                    edit();
                    break;
                case "4":
                    delete();
                    break;
                case "5":
                    search();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Nhap sai, moi nhap lai");
                    break;
            }
        } while (!select.equals("0"));
        sleep();
    }

    //add new employee
    public void add() {
        Employee employee = new Employee();
        System.out.println("||==================  Them nhan vien  ===================||");
        //chosen department
        System.out.println("|| Chon phong bang: ");
        String departmentId = sc.nextLine();
        //validate department
        while (departmentManagement.findById(departmentId) == null || departmentId.isEmpty()) {
            System.out.println("|| Khong tim thay phong ban , vui long chon lai ");
            departmentId = sc.nextLine();
        }
        //check quantity of department
        List<Employee> employeeList = this.findByDepartmentId(departmentId);
        String departmentQuantity = departmentManagement.findById(departmentId).getQuantity();
        while (Integer.parseInt(departmentQuantity) <= employeeList.size()) {
            System.out.println("|| Phong ban da day , vui long chon lai ");
            departmentId = sc.nextLine();
        }
        employee.setDepartmentId(departmentId);
        employee.setId(SystemService.generateId("NV"));
        System.out.print("Nhap ten nhan vien: ");
        employee.setName(sc.nextLine());
        System.out.print("Nhap tuoi nhan vien: ");
        employee.setAge(sc.nextLine());
        System.out.print("Nhap gioi tinh: ");
        employee.setGender(sc.nextLine());
        System.out.print("Nhap so dien thoai: ");
        employee.setPhone(sc.nextLine());
        System.out.print("Nhap email nhan vien: ");
        employee.setEmail(sc.nextLine());
        System.out.print("Nhap luong nhan vien (luong/gio): ");
        String salary = sc.nextLine();
        //validate salary
        while (!isNumber(salary)) {
            System.out.println("|| Luong phai la so , vui long nhap lai ");
            salary = sc.nextLine();
        }
        employee.setSalary(Integer.parseInt(salary));
        System.out.print("Nhap chuc vu nhan vien: ");
        employee.setPermission(sc.nextLine());
        EmployeeManagement.employeeList.add(employee);
        this.writeFile();
    }

    //show all employee
    public void show() {
        System.out.println("||==================  Danh sach nhan vien  ===================||");
        for (Employee employee : employeeList) {
            this.PrintEmployee(employee);
        }
        System.out.println("||============================================================||");
    }

    //edit employee
    public void edit() {
        System.out.println("||==================  Sua thong tin nhan vien  ===================||");
        System.out.println("Nhap ma nhan vien can sua: ");
        String id = sc.nextLine();
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("Khong tim thay nhan vien");
        } else {
            String select;
            do {
                System.out.println("||==================  Thong tin nhan vien  ===================||");
                this.PrintEmployee(employee);
                System.out.println("||============================================================||");
                System.out.println("||==================  Chon thong tin can sua  ===================||");
                System.out.println("|| 1. Sua ten nhan vien                     ||");
                System.out.println("|| 2. Sua tuoi nhan vien                    ||");
                System.out.println("|| 3. Sua gioi tinh nhan vien               ||");
                System.out.println("|| 4. Sua so dien thoai nhan vien           ||");
                System.out.println("|| 5. Sua email nhan vien                   ||");
                System.out.println("|| 6. Sua luong nhan vien (luong/gio)       ||");
                System.out.println("|| 7. Sua phong ban nhan vien               ||");
                System.out.println("|| 8. Sua chuc vu nhan vien                 ||");
                System.out.println("|| 0. Exit                                  ||");
                System.out.println("||===========================================||");
                select = sc.nextLine();
                switch (select) {
                    case "1":
                        System.out.println("Nhap ten nhan vien moi: ");
                        employee.setName(sc.nextLine());
                        this.writeFile();
                        break;
                    case "2":
                        System.out.println("Nhap tuoi nhan vien moi: ");
                        employee.setAge(sc.nextLine());
                        this.writeFile();
                        break;
                    case "3":
                        System.out.println("Nhap gioi tinh nhan vien moi: ");
                        employee.setGender(sc.nextLine());
                        this.writeFile();
                        break;
                    case "4":
                        System.out.println("Nhap so dien thoai nhan vien moi: ");
                        employee.setPhone(sc.nextLine());
                        this.writeFile();
                        break;
                    case "5":
                        System.out.println("Nhap email nhan vien moi: ");
                        employee.setEmail(sc.nextLine());
                        this.writeFile();
                        break;
                    case "6":
                        System.out.println("Nhap luong nhan vien moi: ");
                        String salary = sc.nextLine();
                        while (!isNumber(salary)) {
                            System.out.println("|| Luong phai la so , vui long nhap lai ");
                            salary = sc.nextLine();
                        }
                        employee.setSalary(Integer.parseInt(salary));
                        this.writeFile();
                        break;
                    case "7":
                        System.out.println("Nhap phong ban nhan vien moi: ");
                        String departmentId = sc.nextLine();
                        //validate department
                        while (departmentManagement.findById(departmentId) == null || departmentId.isEmpty()) {
                            System.out.println("|| Khong tim thay phong ban , vui long chon lai ");
                            departmentId = sc.nextLine();
                        }
                        //check quantity of department
                        List<Employee> employeeList = this.findByDepartmentId(departmentId);
                        String departmentQuantity = departmentManagement.findById(departmentId).getQuantity();
                        while (Integer.parseInt(departmentQuantity) <= employeeList.size()) {
                            System.out.println("|| Phong ban da day , vui long chon lai ");
                            departmentId = sc.nextLine();
                        }
                        employee.setDepartmentId(departmentId);
                        this.writeFile();
                        break;
                    case "8":
                        System.out.println("Nhap chuc vu nhan vien moi: ");
                        employee.setPermission(sc.nextLine());
                        this.writeFile();
                        break;
                    case "0":
                        break;
                }
            } while (!select.equals("0"));
        }
    }

    //delete employee
    public void delete() {
        System.out.println("||==================  Xoa nhan vien  ===================||");
        System.out.println("Nhap ma nhan vien can xoa: ");
        String id = sc.nextLine();
        Employee employee = findById(id);
        if (employee == null) {
            System.out.println("Khong tim thay nhan vien");
        } else {
            employeeList.remove(employee);
            System.out.println("Xoa nhan vien thanh cong");
            this.writeFile();
        }
    }

    public void search() {
        System.out.println("||==================  Tim kiem nhan vien  ===================||");
        String select;
        do {
            System.out.println("1. Tim kiem theo id");
            System.out.println("2. Tim kiem theo ten");
            System.out.println("3. Tim kiem theo phong ban");
            System.out.println("4. Tim kiem theo chuc vu");
            System.out.println("0. Exit");
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    System.out.println("Nhap id nhan vien can tim kiem: ");
                    String id = sc.nextLine();
                    Employee employee = findById(id);
                    if (employee == null) {
                        System.out.println("Khong tim thay nhan vien");
                    } else {
                        this.PrintEmployee(employee);
                    }
                }
                case "2" -> {
                    System.out.println("Nhap ten nhan vien can tim kiem: ");
                    String name = sc.nextLine();
                    List<Employee> employeeList = findByName(name);
                    if (employeeList.isEmpty()) {
                        System.out.println("Khong tim thay nhan vien");
                    } else {
                        for (Employee employee1 : employeeList) {
                            this.PrintEmployee(employee1);
                        }
                    }
                }
                case "3" -> {
                    System.out.println("Nhap id phong ban nhan vien can tim kiem: ");
                    String departmentId = sc.nextLine();
                    List<Employee> employeeList = findByDepartmentId(departmentId);
                    if (employeeList.isEmpty()) {
                        System.out.println("Khong tim thay nhan vien");
                    } else {
                        for (Employee employee : employeeList) {
                            this.PrintEmployee(employee);
                        }
                    }
                }
                case "4" -> {
                    System.out.println("Nhap chuc vu nhan vien can tim kiem: ");
                    String permission = sc.nextLine();
                    List<Employee> employeeList = findByPermission(permission);
                    if (employeeList.isEmpty()) {
                        System.out.println("Khong tim thay nhan vien");
                    } else {
                        for (Employee employee : employeeList) {
                            this.PrintEmployee(employee);
                        }
                    }
                }
                case "0" -> System.out.println("Thoat chuc nang tim kiem");
                default -> System.out.println("Nhap sai, moi nhap lai");
            }
        }
        while (!select.equals("0"));
    }

    //file writer
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            for (Employee employee : employeeList) {
                fileWriter.append(employee.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getName());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getAge());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getGender());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getPhone());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getEmail());
                fileWriter.append(DELIMITER);
                fileWriter.append(String.valueOf(employee.getSalary()));
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getDepartmentId());
                fileWriter.append(DELIMITER);
                fileWriter.append(employee.getPermission());
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Loi ghi file");
        }
    }


    //read file
    public void readFile() {
        try {
            FileReader fr = new FileReader(FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] arr = line.split(SPLIT_PATTERN);
                Employee employee = new Employee();
                employee.setId(arr[0]);
                employee.setName(arr[1]);
                employee.setAge(arr[2]);
                employee.setGender(arr[3]);
                employee.setPhone(arr[4]);
                employee.setEmail(arr[5]);
                employee.setSalary(Integer.parseInt(arr[6]));
                employee.setDepartmentId(arr[7]);
                employee.setPermission(arr[8]);
                employeeList.add(employee);
            }
        } catch (Exception e) {
            System.out.println("Loi doc file");
        }


    }
    //endregion

    //to print  employee view

    private void PrintEmployee(Employee employee) {
        Department department = departmentManagement.findById(employee.getDepartmentId());
        if (department == null) {
            System.out.println(" Ma nhan vien :" + employee.getId() + " Phong ban nhan vien nay khong ton tai");
            return;
        }
        String departmentName = department.getName();
        System.out.println("==============================================");
        System.out.println("Ma nhan vien: " + employee.getId());
        System.out.println("Ten nhan vien: " + employee.getName());
        System.out.println("Tuoi: " + employee.getAge());
        System.out.println("Gioi tinh: " + employee.getGender());
        System.out.println("So dien thoai: " + employee.getPhone());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Luong: " + employee.getSalary());
        System.out.println("Phong ban: " + departmentName);
        System.out.println("Chuc vu: " + employee.getPermission());
        System.out.println("==============================================");
    }

}
