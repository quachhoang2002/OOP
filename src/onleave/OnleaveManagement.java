package onleave;

import base.SystemService;
import employee.Employee;
import employee.EmployeeManagement;
import shift.Shift;
import shift.ShiftManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnleaveManagement extends SystemService {
    private static final String FILE_PATH = "onleave.txt";
    protected static List<Onleave> onleaveList = new ArrayList<>();

    protected EmployeeManagement employeeManagement;
    protected ShiftManagement shiftManagement;

    public OnleaveManagement() {
        employeeManagement = new EmployeeManagement();
        shiftManagement = new ShiftManagement();
        if (onleaveList.isEmpty()) {
            readFile();
        }
    }

    //region base method
    public List<Onleave> getOnleaveList() {
        return onleaveList;
    }

    public Onleave findById(String id) {
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            if (onleave.getId().equals(id)) {
                return onleave;
            }
        }
        return null;
    }

    public List<Onleave> findByEmployeeId(String employeeId) {
        List<Onleave> onleaveList = new ArrayList<>();
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            if (onleave.getEmployeeId().equals(employeeId)) {
                onleaveList.add(onleave);
            }
        }
        return onleaveList;
    }

    public List<Onleave> findByDate(String date) {
        List<Onleave> onleaveList = new ArrayList<>();
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            if (onleave.getDate().equals(date)) {
                onleaveList.add(onleave);
            }
        }
        return onleaveList;
    }
    //endregion


    //ShowMenu
    public void showMenu() {
        String select;
        do {
            System.out.println("||============== Quan ly nghi phep ==============||");
            System.out.println("|| 1. Them nghi phep.                           ||");
            System.out.println("|| 2. Xoa nghi phep.                            ||");
            System.out.println("|| 3. Sua nghi phep.                            ||");
            System.out.println("|| 4. Xem danh sach nghi phep.                  ||");
            System.out.println("|| 5. Tim kiem nghi phep.                       ||");
            System.out.println("|| 0. Thoat.                                    ||");
            System.out.println("||==============================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> this.add();
                case "2" -> this.delete();
                case "3" -> this.edit();
                case "4" -> this.show();
                case "5" -> this.search();
                case "0" -> System.out.println("Thoat");
                default -> System.out.println("Nhap sai");
            }
        } while (!select.equals("0"));
    }

    //add
    public void add() {
        String id = SystemService.generateId("NP");
        System.out.println("Nhap ma nhan vien: ");
        String employeeId = sc.nextLine();
        //validate employeeId;
        while (employeeManagement.findById(employeeId) == null) {
            System.out.println("Ma nhan vien khong ton tai. Nhap lai: ");
            employeeId = sc.nextLine();
        }
        System.out.println("Nhap ma ca: ");
        String shiftId = sc.nextLine();
        //validate shiftId
        ShiftManagement shift = this.shiftManagement;
        shift.readFile();
        while (shift.findById(shiftId) == null) {
            System.out.println("Khong ton tai ca , moi nhap lai");
            shiftId = sc.nextLine();
        }
        String date = "";
        try {
            date = this.dateInput();
        } catch (Exception e) {
            System.out.println("Nhap sai dinh dang ngay");
            return;
        }

        System.out.println("Nhap ly do nghi phep: ");
        String reason = sc.nextLine();
        Onleave onleave = new Onleave(id, employeeId, shiftId, date, reason);
        OnleaveManagement.onleaveList.add(onleave);
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
            OnleaveManagement.onleaveList.remove(onleave);
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
            String select;
            do {
                this.printOnleave(onleave);
                System.out.println("||============== Sua nghi phep ==============||");
                System.out.println("|| 1. Sua ma nhan vien.                      ||");
                System.out.println("|| 2. Sua ma ca.                             ||");
                System.out.println("|| 3. Sua ngay nghi phep.                    ||");
                System.out.println("|| 4. Sua ly do nghi phep.                   ||");
                System.out.println("|| 0. Thoat.                                 ||");
                System.out.println("||===========================================||");
                select = sc.nextLine();
                switch (select) {
                    case "1" -> {
                        System.out.println("Nhap ma nhan vien: ");
                        String employeeId = sc.nextLine();
                        //validate employeeId;
                        while (employeeManagement.findById(employeeId) == null) {
                            System.out.println("Ma nhan vien khong ton tai. Nhap lai: ");
                            employeeId = sc.nextLine();
                        }
                        onleave.setEmployeeId(employeeId);
                        this.writeFile();
                    }
                    case "2" -> {
                        System.out.println("Nhap ma ca: ");
                        String shiftId = sc.nextLine();
                        //validate shiftId
                        ShiftManagement shift = this.shiftManagement;
                        shift.readFile();
                        while (shift.findById(shiftId) == null) {
                            System.out.println("Khong ton tai ca , moi nhap lai");
                            shiftId = sc.nextLine();
                        }
                        onleave.setShiftId(shiftId);
                        this.writeFile();
                    }
                    case "3" -> {
                        String date = "";
                        try {
                            date = this.dateInput();
                        } catch (Exception e) {
                            System.out.println("Nhap sai dinh dang ngay");
                            break;
                        }
                        onleave.setDate(date);
                        this.writeFile();
                    }
                    case "4" -> {
                        System.out.println("Nhap ly do nghi phep: ");
                        String reason = sc.nextLine();
                        onleave.setReason(reason);
                        this.writeFile();
                    }
                    case "0" -> System.out.println("Thoat");
                    default -> System.out.println("Nhap sai");
                }
            } while (!select.equals("0"));
        }
    }

    //search
    public void search() {
        String select;
        do {
            System.out.println("||============== Tim kiem nghi phep ==============||");
            System.out.println("|| 1. Tim kiem theo ma nhan vien.                ||");
            System.out.println("|| 2. Tim kiem theo ngay nghi phep.              ||");
            System.out.println("|| 3. Tim kiem theo ma nghi phep.                ||");
            System.out.println("|| 0. Thoat.                                     ||");
            System.out.println("||===============================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    System.out.println("Nhap ma nhan vien: ");
                    String employeeId = sc.nextLine();
                    List<Onleave> onleaveList = this.findByEmployeeId(employeeId);
                    if (onleaveList.isEmpty()) {
                        System.out.println("Khong tim thay nghi phep nao");
                    } else {
                        for (Onleave onleave : onleaveList) {
                            printOnleave(onleave);
                        }
                    }
                }
                case "2" -> {
                    String date = "";
                    try {
                        date = this.dateInput();
                    } catch (Exception e) {
                        System.out.println("Nhap sai dinh dang ngay");
                        return;
                    }
                    List<Onleave> onleaveList = this.findByDate(date);
                    if (onleaveList.isEmpty()) {
                        System.out.println("Khong tim thay nghi phep nao");
                    } else {
                        for (Onleave onleave : onleaveList) {
                            printOnleave(onleave);
                        }
                    }
                }
                case "3" -> {
                    System.out.println("Nhap ma nghi phep: ");
                    String id = sc.nextLine();
                    Onleave onleave = this.findById(id);
                    if (onleave == null) {
                        System.out.println("Khong ton tai nghi phep nay");
                    } else {
                        printOnleave(onleave);
                    }
                }
                default -> System.out.println("Nhap sai");
            }
        } while (!select.equals("0"));
    }

    //showList
    public void show() {
        System.out.println("||================= Danh sach nghi phep =================||");
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            printOnleave(onleave);
        }
        System.out.println("||======================================================||");
    }

  

    private String dateInput() {
        String date = "";
        boolean check = false;
        while (!check) {
            System.out.println("Nhap ngay nghi phep: ");
            String day = sc.nextLine();
            System.out.println("Thang nghi phep: ");
            String month = sc.nextLine();
            System.out.println("Nam nghi phep: ");
            String year = sc.nextLine();
            date = day + "-" + month + "-" + year;
            check = this.checkDate(date);
            if (!check) {
                System.out.println("Ngay nghi phep khong hop le. Nhap lai: ");
            }
        }
        return date;
    }

    //writeFile
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("onleave.txt");
            for (Onleave onleave : OnleaveManagement.onleaveList) {
                fileWriter.append(onleave.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(onleave.getEmployeeId());
                fileWriter.append(DELIMITER);
                fileWriter.append(onleave.getShiftId());
                fileWriter.append(DELIMITER);
                fileWriter.append(onleave.getDate());
                fileWriter.append(DELIMITER);
                fileWriter.append(onleave.getReason());
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
            FileReader fileReader = new FileReader(OnleaveManagement.FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] onleave = line.split(SPLIT_PATTERN);
                OnleaveManagement.onleaveList.add(new Onleave(onleave[0], onleave[1], onleave[2], onleave[3], onleave[4]));
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printOnleave(Onleave onleave) {
        Employee employee = employeeManagement.findById(onleave.getEmployeeId());
        Shift shift = shiftManagement.findById(onleave.getShiftId());
        if (employee == null || shift == null) {
            System.out.println("Ma nghi phep :" + onleave.getId() + " khong ton tai nhan vien hoac ca lam viec");
            return;
        }
        String employeeName = employee.getName();
        String shiftName = shift.getName();
        System.out.println("||=====================================||");
        System.out.println("|| Ma nghi phep: " + onleave.getId());
        System.out.println("|| Ten nhan vien: " + employeeName);
        System.out.println("|| Ten ca lam viec: " + shiftName);
        System.out.println("|| Ngay nghi phep: " + onleave.getDate());
        System.out.println("|| Ly do nghi phep: " + onleave.getReason());
        System.out.println("||=======================================||");
    }

}
