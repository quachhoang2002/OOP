package shift;

import base.SystemService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ShiftManagement extends SystemService {
    private static final String FILE_NAME = "shift.txt";
    private static List<Shift> shiftList = new ArrayList<>();

    public ShiftManagement() {
        this.readFile();
    }

    //region implement methods

    //get list shift
    public List<Shift> getShiftList() {
        return ShiftManagement.shiftList;
    }

    //find by id
    public Shift findById(String id) {
        for (Shift shift : ShiftManagement.shiftList) {
            if (shift.getId().equals(id)) {
                return shift;
            }
        }
        return null;
    }

    //find by name
    public Shift findByName(String name) {
        for (Shift shift : ShiftManagement.shiftList) {
            if (shift.getName().equals(name)) {
                return shift;
            }
        }
        return null;
    }

    //find by start time
    public Shift findByStartTime(String startTime) {
        for (Shift shift : ShiftManagement.shiftList) {
            if (shift.getStartTime().equals(startTime)) {
                return shift;
            }
        }
        return null;
    }

    //find by end time
    public Shift findByEndTime(String endTime) {
        for (Shift shift : ShiftManagement.shiftList) {
            if (shift.getEndTime().equals(endTime)) {
                return shift;
            }
        }
        return null;
    }

    //endregion

    //region implement base methods

    //show menu
    public void showMenu() {
        String select;
        do {
            System.out.println("||============== Quan ly ca ==============||");
            System.out.println("|| 1. Them ca                             ||");
            System.out.println("|| 2. Sua ca                              ||");
            System.out.println("|| 3. Xoa ca                              ||");
            System.out.println("|| 4. Hien thi danh sach ca               ||");
            System.out.println("|| 5. Tim kiem ca                         ||");
            System.out.println("|| 0. Thoat                               ||");
            System.out.println("||=======================================||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    this.add();
                    break;
                case "2":
                    this.edit();
                    break;
                case "3":
                    this.delete();
                    break;
                case "4":
                    this.show();
                    break;
                case "5":
                    this.search();
                    break;
                case "0":
                    System.out.println("Thoat chuong trinh");
                    break;
                default:
                    System.out.println("Lua chon khong hop le");
                    break;
            }
        } while (!select.equals("0"));
    }

    //add
    public void add() {
        String id = this.generateId("CA");
        System.out.println("Nhap ten ca: ");
        String name = sc.nextLine();
        System.out.println("Nhap gio bat dau: ");
        String startTime = sc.nextLine();
        //validate start time
        while (!validateTime(startTime)) {
            System.out.println("Gio bat dau khong hop le. Vui long nhap lai");
            startTime = sc.nextLine();
        }
        System.out.println("Nhap gio ket thuc: ");
        String endTime = sc.nextLine();
        //validate end time
        while (!validateTime(endTime)) {
            System.out.println("Gio ket thuc khong hop le. Vui long nhap lai");
            endTime = sc.nextLine();
        }
        //validate end time > start time
        while (!this.validateStartEndTime(endTime, startTime)) {
            System.out.println("Gio ket thuc phai lon hon gio bat dau. Vui long nhap lai");
            endTime = sc.nextLine();
        }
        Shift shift = new Shift(id, name, startTime, endTime,Integer.toString(calculateTimeWorking(startTime,endTime)));
        ShiftManagement.shiftList.add(shift);
        this.writeFile();
    }

    //edit
    public void edit() {
        System.out.println("Nhap ma ca can sua: ");
        String id = sc.nextLine();
        Shift shift = this.findById(id);
        if (shift != null) {
            System.out.println("Nhap ten ca moi: ");
            String name = sc.nextLine();
            System.out.println("Nhap gio bat dau moi: ");
            String startTime = sc.nextLine();
            System.out.println("Nhap gio ket thuc moi: ");
            String endTime = sc.nextLine();
            shift.setName(name);
            shift.setStartTime(startTime);
            shift.setEndTime(endTime);
            this.writeFile();
        } else {
            System.out.println("Khong tim thay ca can sua");
        }
    }

    //delete
    public void delete() {
        System.out.println("Nhap ma ca can xoa: ");
        String id = sc.nextLine();
        Shift shift = this.findById(id);
        if (shift != null) {
            ShiftManagement.shiftList.remove(shift);
            this.writeFile();
        } else {
            System.out.println("Khong tim thay ca can xoa");
        }
    }

    //show
    public void show() {
        System.out.println("||================= Danh sach ca =================||");
        for (Shift shift : ShiftManagement.shiftList) {
            this.printShift(shift);
            System.out.println("--------------------------------------------------");
        }
        System.out.println("||================================================||");
    }

    //search
    public void search() {
        String select;
        do {
            System.out.println("||================= Tim kiem ca =================||");
            System.out.println("|| 1. Tim kiem theo ma ca                        ||");
            System.out.println("|| 2. Tim kiem theo ten ca                       ||");
            System.out.println("|| 3. Tim kiem theo gio bat dau                  ||");
            System.out.println("|| 4. Tim kiem theo gio ket thuc                 ||");
            System.out.println("|| 0. Thoat                                      ||");
            System.out.println("||===============================================||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    System.out.println("Nhap ma ca can tim kiem: ");
                    String id = sc.nextLine();
                    Shift shift = this.findById(id);
                    if (shift != null) {
                        this.printShift(shift);
                    } else {
                        System.out.println("Khong tim thay ca can tim kiem");
                    }
                    break;
                case "2":
                    System.out.println("Nhap ten ca can tim kiem: ");
                    String name = sc.nextLine();
                    shift = this.findByName(name);
                    if (shift != null) {
                        this.printShift(shift);
                    } else {
                        System.out.println("Khong tim thay ca can tim kiem");
                    }
                    break;
                case "3":
                    System.out.println("Nhap gio bat dau can tim kiem: ");
                    String startTime = sc.nextLine();
                    shift = this.findByStartTime(startTime);
                    if (shift != null) {
                        this.printShift(shift);
                    } else {
                        System.out.println("Khong tim thay ca can tim kiem");
                    }
                    break;
                case "4":
                    System.out.println("Nhap gio ket thuc can tim kiem: ");
                    String endTime = sc.nextLine();
                    shift = this.findByEndTime(endTime);
                    if (shift != null) {
                        this.printShift(shift);
                    } else {
                        System.out.println("Khong tim thay ca can tim kiem");
                    }
                    break;
                case "0":
                    System.out.println("Thoat chuong trinh");
                    break;
                default:
                    System.out.println("Lua chon khong hop le");
                    break;
            }
        }while (!select.equals("0"));

    }

    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            for (Shift shift : ShiftManagement.shiftList) {
                fileWriter.append(shift.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(shift.getName());
                fileWriter.append(DELIMITER);
                fileWriter.append(shift.getStartTime());
                fileWriter.append(DELIMITER);
                fileWriter.append(shift.getEndTime());
                fileWriter.append(DELIMITER);
                fileWriter.append(shift.getWorkingTime());
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Loi ghi file");
        }
    }

    public void readFile() {
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(SPLIT_PATTERN);
                Shift shift = new Shift(arr[0], arr[1], arr[2], arr[3], arr[4]);
                ShiftManagement.shiftList.add(shift);
            }
        } catch (Exception e) {
            System.out.println("Loi doc file");
        }

    }
    //endregion


    //validate endtime > starttime
    private boolean validateStartEndTime(String endTime, String startTime) {
        String[] start = this.splitTime(startTime);
        String[] end = this.splitTime(endTime);
        int startHour = Integer.parseInt(start[0]);
        int startMinute = Integer.parseInt(start[1]);
        int endHour = Integer.parseInt(end[0]);
        int endMinute = Integer.parseInt(end[1]);
        if (endHour > startHour) {
            return true;
        } else if (endHour == startHour) {
            if (endMinute > startMinute) {
                return true;
            }
        }
        return false;
    }

    //calculate time working
    private int calculateTimeWorking(String startTime, String endTime) {
        String[] start = this.splitTime(startTime);
        String[] end = this.splitTime(endTime);
        int startHour = Integer.parseInt(start[0]);
        int startMinute = Integer.parseInt(start[1]);
        int endHour = Integer.parseInt(end[0]);
        int endMinute = Integer.parseInt(end[1]);
        int timeWorking = (endHour - startHour) * 60 + (endMinute - startMinute);
        return timeWorking;
    }
    private void printShift(Shift shift) {
        System.out.println("Ma ca: " + shift.getId());
        System.out.println("Ten ca: " + shift.getName());
        System.out.println("Gio bat dau: " + shift.getStartTime());
        System.out.println("Gio ket thuc: " + shift.getEndTime());
        System.out.println("Thoi gian lam viec: " + shift.getWorkingTime() + " phut");
    }

}
