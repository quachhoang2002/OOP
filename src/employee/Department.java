package employee;

import base.BaseService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Department extends BaseService implements IDeparment {
    private String id;
    private String name;
    private String quantity;
    private static List<Department> departmentList = new ArrayList<Department>();

    public Department() {
        this.id = "";
        this.name = "";
        this.quantity = "";
    }

    public Department(String id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //find by id
    public Department findById(String id) {
        for (Department department : departmentList) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        return null;
    }

    //get department list
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void showMenu() {
        this.readFile();
        String select;
        do {
            System.out.println("||================== Menu ===================||");
            System.out.println("||1. Them phong ban                          ||");
            System.out.println("||2. Sua thong tin phong ban                 ||");
            System.out.println("||3. Xoa thong tin phong ban                 ||");
            System.out.println("||4. Hien thi thong tin phong ban            ||");
            System.out.println("||5. Tim Phong Ban                           ||");
            System.out.println("||0. Thoat                                   ||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    add();
                    break;
                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    show();
                    break;
                case "5":
                    search();
                    break;
                case "0":
                    System.out.println("BAN DA THOAT CHUC NANG PHONG BAN");
                    break;
                default:
                    System.out.println("Nhap sai lua chon, xin nhap lai !!!");
            }
        } while (!select.equals("0"));
        sleep();
    }

    //create
    public void add() {
        System.out.println("||================== Them Phong Ban ===================||");
        String id = this.generateId();
        System.out.println("Nhap ten phong ban: ");
        String name = sc.nextLine();
        System.out.println("Nhap so luong nhan vien: ");
        String quantity = sc.nextLine();
        while (!isNumber(quantity)) {
            System.out.println("So luong nhan vien phai la so,hay nhap lai: ");
            quantity = sc.nextLine();
        }
        Department department = new Department(id, name, quantity);
        departmentList.add(department);
        System.out.println("||================== Them Thanh Cong ===================||");
        this.writeFile();
    }

    public void show() {
        System.out.println("||================== Danh Sach Phong Ban ===================||");
        for (Department department : departmentList) {
            this.printItem(department);
            System.out.println("==============================================");
        }
    }

    public void delete() {
        System.out.println("||================== Xoa Phong Ban ===================||");
        System.out.println("Nhap ma phong can xoa: ");
        String id = sc.nextLine();
        Department department = findById(id);
        if (department != null) {
            departmentList.remove(department);
            this.writeFile();
            System.out.println("Xoa thanh cong");
        } else {
            System.out.println("Khong tim thay phong ban");
        }
    }

    //update
    public void edit() {
        System.out.println("||================== Sua Thong Tin Phong Ban ===================||");
        System.out.println("Nhap ma phong can sua: ");
        String id = sc.nextLine();
        Department department = findById(id);
        if (department == null) {
            System.out.println("Khong tim thay phong ban");
        } else {
            String select;
            do {
                System.out.println("============= Thong tin phong ban =============");
                this.printItem(department);
                System.out.println("==============================================");
                System.out.println("||================== Menu ===================||");
                System.out.println("||1. Sua ten phong ban                       ||");
                System.out.println("||2. Sua so luong nhan vien                  ||");
                System.out.println("||0. Thoat                                   ||");
                select = sc.nextLine();
                switch (select) {
                    case "1":
                        System.out.println("Nhap ten phong ban moi: ");
                        String name = sc.nextLine();
                        department.setName(name);
                        System.out.println("Sua thanh cong");
                        break;
                    case "2":
                        System.out.println("Nhap so luong nhan vien moi: ");
                        String quantity = sc.nextLine();
                        while (!isNumber(quantity)) {
                            System.out.println("Nhap sai, moi nhap lai so luong nhan vien: ");
                            quantity = sc.nextLine();
                        }
                        department.setQuantity(quantity);
                        System.out.println("Sua thanh cong");
                        break;
                    default:
                        System.out.println("Nhap sai lua chon, xin nhap lai !!!");
                        this.sleep();
                }
            } while (!select.equals("0"));
            this.writeFile();
        }

    }

    //search
    public void search() {
        System.out.println("||================== Tim Phong Ban ===================||");
        System.out.println("Nhap ma phong ban can tim: ");
        String id = sc.nextLine();
        Department department = findById(id);
        if (department != null) {
            this.printItem(department);
            System.out.println("============================================");
        } else {
            System.out.println("Khong tim thay phong ban");
        }
    }

    //writeFile
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("Department.txt");
            for (Department department : departmentList) {
                fileWriter.write(department.getId() + "|" + department.getName() + "|" + department.getQuantity());
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Loi ghi file");
        }
    }

    //read file
    public void readFile() {
        try {
            FileReader fr = new FileReader("Department.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                String[] arr = line.split("\\|");
                Department department = new Department(arr[0], arr[1], arr[2]);
                departmentList.add(department);
            }
            fr.close();
            br.close();
        } catch (IOException e) {
            System.out.println("Loi doc file");
        }
    }

    private void printItem(Department department) {
        System.out.println("Ma phong: " + department.getId());
        System.out.println("Ten phong: " + department.getName());
        System.out.println("So luong nhan vien: " + department.getQuantity());
    }

}
