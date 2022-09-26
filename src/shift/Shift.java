package shift;

import base.BaseService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Shift extends BaseService implements IShift {
    private String id;
    private String name;
    private String startTime;
    private String endTime;

    private static List<Shift> shiftList = new ArrayList<>();

    public Shift() {
        this.id = "";
        this.name = "";
        this.startTime = "";
        this.endTime = "";
    }

    public Shift(String id, String name, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    //show menu
    public void showMenu() {
        this.readFile();
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
        String id = this.generateId();
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
        Shift shift = new Shift(id, name, startTime, endTime);
        Shift.shiftList.add(shift);
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
            Shift.shiftList.remove(shift);
            this.writeFile();
        } else {
            System.out.println("Khong tim thay ca can xoa");
        }
    }

    //show
    public void show() {
        System.out.println("||================= Danh sach ca =================||");
        for (Shift shift : Shift.shiftList) {
            this.printShift(shift);
        }
        System.out.println("||================================================||");
    }

    //search
    public void search() {
        System.out.println("Nhap ma ca can tim: ");
        String id = sc.nextLine();
        Shift shift = this.findById(id);
        if (shift != null) {
            System.out.println("||================= Thong tin ca =================||");
            this.printShift(shift);
            System.out.println("||================================================||");
        } else {
            System.out.println("Khong tim thay ca can tim");
        }
    }

    //find by id
    public Shift findById(String id) {
        for (Shift shift : Shift.shiftList) {
            if (shift.getId().equals(id)) {
                return shift;
            }
        }
        return null;
    }

    //calculate time working
    public int calculateTimeWorking(String startTime, String endTime) {
        String[] start = startTime.split(":");
        String[] end = endTime.split(":");
        int startHour = Integer.parseInt(start[0]);
        int startMinute = Integer.parseInt(start[1]);
        int endHour = Integer.parseInt(end[0]);
        int endMinute = Integer.parseInt(end[1]);
        int timeWorking = (endHour - startHour) * 60 + (endMinute - startMinute);
        return timeWorking;
    }

    //validate endtime > starttime
    private boolean validateStartEndTime(String endTime, String startTime) {
        String[] start = startTime.split(":");
        String[] end = endTime.split(":");
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


    //get list shift
    public List<Shift> getShiftList() {
        return Shift.shiftList;
    }

    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("shift.txt");
            for (Shift shift : Shift.shiftList) {
                fileWriter.write(shift.getId() + "|" + shift.getName() + "|" + shift.getStartTime() + "|" + shift.getEndTime());
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Loi ghi file");
        }
    }

    public void readFile() {
        try {
            FileReader fileReader = new FileReader("shift.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split("\\|");
                Shift shift = new Shift(arr[0], arr[1], arr[2], arr[3]);
                Shift.shiftList.add(shift);
            }
        } catch (Exception e) {
            System.out.println("Loi doc file");
        }

    }

    private void printShift(Shift shift) {
        System.out.println("Ma ca: " + shift.getId());
        System.out.println("Ten ca: " + shift.getName());
        System.out.println("Gio bat dau: " + shift.getStartTime());
        System.out.println("Gio ket thuc: " + shift.getEndTime());
        System.out.println("Thoi gian lam viec: " + this.calculateTimeWorking(shift.getStartTime(), shift.getEndTime()) + " phut");
    }
}


