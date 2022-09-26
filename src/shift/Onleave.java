package shift;

import base.BaseService;
import employee.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Onleave extends BaseService implements IOnleave {
    private String id;
    private String employeeId;
    private String shiftId;
    private String date;
    private String reason;

    private static List<Onleave> onleaveList = new ArrayList<>();

    public Onleave() {
        this.id = "";
        this.employeeId = "";
        this.shiftId = "";
        this.date = "";
        this.reason = "";
    }

    public Onleave(String id, String employeeId, String shiftId, String date, String reason) {
        this.id = id;
        this.employeeId = employeeId;
        this.shiftId = shiftId;
        this.date = date;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //ShowMenu
    public void show() {
        this.readFile();
        String select;
        do {
            System.out.println("||============== Quan ly nghi phep ==============||");
            System.out.println("|| 1. Them nghi phep.                           ||");
            System.out.println("|| 2. Xoa nghi phep.                            ||");
            System.out.println("|| 3. Sua nghi phep.                            ||");
            System.out.println("|| 4. Xem danh sach nghi phep.                  ||");
            System.out.println("|| 0. Thoat.                                    ||");
            System.out.println("||==============================================||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    this.add();
                    break;
                case "2":
                    this.delete();
                    break;
                case "3":
                    this.edit();
                    break;
                case "4":
                    this.showList();
                    break;
                case "0":
                    System.out.println("Thoat");
                    break;
                default:
                    System.out.println("Nhap sai");
                    break;
            }
        } while (select.equals("0"));
    }

    //add
    public void add() {
        String id = this.generateId();
        System.out.println("Nhap ma nhan vien: ");
        String employeeId = sc.nextLine();
        //validate employeeId
        Employee employee = new Employee();
        employee.readFile();
        while (employee.findById(employeeId) == null) {
            System.out.println("Ma nhan vien khong ton tai. Nhap lai: ");
            employeeId = sc.nextLine();
        }
        System.out.println("Nhap ma ca: ");
        String shiftId = sc.nextLine();
        //validate shiftId
        Shift shift = new Shift();
        shift.readFile();
        while (shift.findById(shiftId) == null) {
            System.out.println("Khong ton tai ca , moi nhap lai");
            shiftId = sc.nextLine();
        }
        System.out.println("Nhap ngay nghi phep: ");
        String date = sc.nextLine();
        System.out.println("Nhap ly do nghi phep: ");
        String reason = sc.nextLine();
        Onleave onleave = new Onleave(id, employeeId, shiftId, date, reason);
        this.onleaveList.add(onleave);
        this.writeFile();
    }

    //delete
    public void delete() {
        System.out.println("Nhap ma nghi phep can xoa: ");
        String id = sc.nextLine();
        Onleave onleave = this.findById(id);
        if (onleave == null) {
            System.out.println("Khong ton tai nghi phep nay");
        } else {
            this.onleaveList.remove(onleave);
            this.writeFile();
        }
    }

    //edit
    public void edit() {
        System.out.println("Nhap ma nghi phep can sua: ");
        String id = sc.nextLine();
        Onleave onleave = this.findById(id);
        if (onleave == null) {
            System.out.println("Khong ton tai nghi phep nay");
        } else {
            System.out.println("Nhap ma nhan vien: ");
            String employeeId = sc.nextLine();
            //validate employeeId
            Employee employee = new Employee();
            employee.readFile();
            while (employee.findById(employeeId) == null) {
                System.out.println("Ma nhan vien khong ton tai. Nhap lai: ");
                employeeId = sc.nextLine();
            }
            System.out.println("Nhap ma ca: ");
            String shiftId = sc.nextLine();
            //validate shiftId
            Shift shift = new Shift();
            shift.readFile();
            while (shift.findById(shiftId) == null) {
                System.out.println("Khong ton tai ca , moi nhap lai");
                shiftId = sc.nextLine();
            }
            System.out.println("Nhap ngay nghi phep: ");
            String date = sc.nextLine();
            System.out.println("Nhap ly do nghi phep: ");
            String reason = sc.nextLine();
            onleave.setEmployeeId(employeeId);
            onleave.setShiftId(shiftId);
            onleave.setDate(date);
            onleave.setReason(reason);
            this.writeFile();
        }
    }


    //showList
    public void showList() {
        System.out.println("||================= Danh sach nghi phep =================||");
        System.out.println("|| Ma nghi phep | Ma nhan vien | Ma ca | Ngay nghi phep ||");
        for (Onleave onleave : Onleave.onleaveList) {
            System.out.println("|| " + onleave.getId() + " | " + onleave.getEmployeeId() + " | " + onleave.getShiftId() + " | " + onleave.getDate() + " ||");
        }
        System.out.println("||======================================================||");
    }

    //findById
    public Onleave findById(String id) {
        for (Onleave onleave : Onleave.onleaveList) {
            if (onleave.getId().equals(id)) {
                return onleave;
            }
        }
        return null;
    }

    //getOnleaveList
    public List<Onleave> getOnleaveList() {
        return onleaveList;
    }

    //writeFile
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("onleave.txt");
            for (Onleave onleave : Onleave.onleaveList) {
                fileWriter.write(onleave.getId() + "|" + onleave.getEmployeeId() + "|" + onleave.getShiftId() + "|" + onleave.getDate() + "|" + onleave.getReason());
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //readFile
    public void readFile() {
        try {
            FileReader fileReader = new FileReader("onleave.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] onleave = line.split("\\|");
                Onleave.onleaveList.add(new Onleave(onleave[0], onleave[1], onleave[2], onleave[3], onleave[4]));
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
