package department;

import base.BaseService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentManagement extends BaseService implements IDepartmenManagement {
    private static final String FILE_PATH = "department.txt";
    private static List<Department> departmentList = new ArrayList<>();


    //region implement method
    public Department findById(String id) {
        for (Department department : departmentList) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        return null;
    }

    public Department findByName(String name) {
        for (Department department : departmentList) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
    }
    //endregion

    //region implement base method

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
        String id = this.generateId("PB");
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
            this.printDepartment(department);
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
                this.printDepartment(department);
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
                    case "0":
                        System.out.println("Thoat chuc nang sua thong tin phong ban");
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
        String select;
        do {
            System.out.println("||================== Tim Phong Ban ===================||");
            System.out.println("||1. Tim theo ma phong ban                          ||");
            System.out.println("||2. Tim theo ten phong ban                         ||");
            System.out.println("||0. Thoat                                          ||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    System.out.println("Nhap ma phong ban can tim: ");
                    String id = sc.nextLine();
                    Department department = findById(id);
                    if (department != null) {
                        System.out.println("||================== Thong Tin Phong Ban ===================||");
                        this.printDepartment(department);
                        System.out.println("============================================================");
                    } else {
                        System.out.println("Khong tim thay phong ban");
                    }
                    break;
                case "2":
                    System.out.println("Nhap ten phong ban can tim: ");
                    String name = sc.nextLine();
                    Department department1 = findByName(name);
                    if (department1 != null) {
                        System.out.println("||================== Thong Tin Phong Ban ===================||");
                        this.printDepartment(department1);
                        System.out.println("============================================================");
                    } else {
                        System.out.println("Khong tim thay phong ban");
                    }
                    break;
                case "0":
                    System.out.println("Thoat chuc nang tim phong ban");
                    break;
                default:
                    System.out.println("Nhap sai lua chon, xin nhap lai !!!");
                    this.sleep();
            }
        } while (!select.equals("0"));
    }

    //writeFile
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            for (Department department : departmentList) {
                fileWriter.append(department.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(department.getName());
                fileWriter.append(DELIMITER);
                fileWriter.append(department.getQuantity());
                fileWriter.append("\n");
            }
            fileWriter.close();
            System.out.println("Ghi file thanh cong");
        } catch (IOException e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }


    //read file
    public void readFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] splitData = line.split(SPLIT_PATTERN);
                if (splitData.length > 0) {
                    Department department = new Department(splitData[0], splitData[1], splitData[2]);
                    departmentList.add(department);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
        }
    }

    //endregion

    private void printDepartment(Department department) {
        System.out.println("Ma phong: " + department.getId());
        System.out.println("Ten phong: " + department.getName());
        System.out.println("So luong nhan vien: " + department.getQuantity());
    }
}
