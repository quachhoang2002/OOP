package onleave;

import base.IAction;
import base.SystemService;
import employee.EmployeeManagement;
import shift.ShiftManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnleaveManagement extends SystemService  {
    private static final String FILE_PATH = "onleave.txt";
    private static List<Onleave> onleaveList = new ArrayList<>();

    private EmployeeManagement employeeManagement ;

    public OnleaveManagement() {
        employeeManagement = new EmployeeManagement();
        this.readFile();
    }
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
                    this.show();
                    break;
                case "5":
                    this.search();
                    break;
                case "0":
                    System.out.println("Thoat");
                    break;
                default:
                    System.out.println("Nhap sai");
                    break;
            }
        } while (!select.equals("0"));
    }

    //add
    public void add() {
        String id = this.generateId("NP");
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
        ShiftManagement shift = new ShiftManagement();
        shift.readFile();
        while (shift.findById(shiftId) == null) {
            System.out.println("Khong ton tai ca , moi nhap lai");
            shiftId = sc.nextLine();
        }
        String date = "";
        try {
            date = this.dateInput();
        }catch (Exception e){
            System.out.println("Nhap sai dinh dang ngay");
            return;
        }

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
            while (employeeManagement.findById(employeeId) == null) {
                System.out.println("Ma nhan vien khong ton tai. Nhap lai: ");
                employeeId = sc.nextLine();
            }
            System.out.println("Nhap ma ca: ");
            String shiftId = sc.nextLine();
            //validate shiftId
            ShiftManagement shift = new ShiftManagement();
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

    //search
    public void search() {
        System.out.println("Nhap ma nghi phep can tim: ");
        String id = sc.nextLine();
        Onleave onleave = this.findById(id);
        if (onleave == null) {
            System.out.println("Khong ton tai nghi phep nay");
        } else {
            System.out.println(onleave);
        }
    }


    //showList
    public void show() {
        System.out.println("||================= Danh sach nghi phep =================||");
        System.out.println("|| Ma nghi phep | Ma nhan vien | Ma ca | Ngay nghi phep ||");
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            System.out.println("|| " + onleave.getId() + " | " + onleave.getEmployeeId() + " | " + onleave.getShiftId() + " | " + onleave.getDate() + " ||");
        }
        System.out.println("||======================================================||");
    }

    //findById
    public Onleave findById(String id) {
        for (Onleave onleave : OnleaveManagement.onleaveList) {
            if (onleave.getId().equals(id)) {
                return onleave;
            }
        }
        return null;
    }

    private static boolean checkDate(String date) {
        String[] dateArr = date.split("-");
        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day > 0 && day <= 31) {
                    return true;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 0 && day <= 30) {
                    return true;
                }
                break;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (day > 0 && day <= 29) {
                        return true;
                    }
                } else {
                    if (day > 0 && day <= 28) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private String dateInput() {
        String date = "";
        boolean check = false;
        while (check == false) {
            System.out.println("Nhap ngay nghi phep: ");
            String day = sc.nextLine();
            System.out.println("Thang nghi phep: ");
            String month = sc.nextLine();
            System.out.println("Nam nghi phep: ");
            String year = sc.nextLine();
            date = day + "-" + month + "-" + year;
            check = this.checkDate(date);
            if (check == false) {
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
            FileReader fileReader = new FileReader(this.FILE_PATH);
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

}
