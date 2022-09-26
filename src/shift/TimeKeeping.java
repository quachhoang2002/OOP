package shift;

import base.BaseService;
import employee.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeKeeping extends BaseService implements ITimeKeeping {
    //attribute
    private String id;
    private String employeeId;
    private String shiftId;
    private String date;
    private String checkIn;
    private String checkOut;
    private static List<TimeKeeping> timeKeepingList = new ArrayList<>();

    //constructor
    public TimeKeeping() {
        this.id = "";
        this.employeeId = "";
        this.shiftId = "";
        this.date = "";
        this.checkIn = "";
        this.checkOut = "";
    }

    public TimeKeeping(String id, String employeeId, String shiftId, String date, String checkIn, String checkOut) {
        this.id = id;
        this.employeeId = employeeId;
        this.shiftId = shiftId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    //getter and setter
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

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    //method
    public TimeKeeping findById(String id) {
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getId().equals(id)) {
                return timeKeeping;
            }
        }
        return null;
    }

    public List<TimeKeeping> getTimeKeepingList() {
        return null;
    }

    //menu
    public void showMenu() {
        this.readFile();
        String select;
        do {
            System.out.println("||=============Chuc nang quan ly cham cong============||");
            System.out.println("||1. Check In                               ||");
            System.out.println("||2. Check Out                              ||");
            System.out.println("||3. Xem cham cong                          ||");
            System.out.println("||0. Thoat                                  ||");
            System.out.println("||===========================================||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    this.checkIn();
                    break;
                case "2":
                    this.checkOut();
                    break;
                case "3":
                    this.showTimeKeeping();
                    break;
                case "0":
                    System.out.println("Thoat chuong trinh");
                    break;
                default:
                    System.out.println("Nhap sai, moi nhap lai");
                    break;
            }
        }
        while (!select.equals("0"));

    }

    //check in
    public void checkIn() {
        //get current time
        LocalDateTime now = LocalDateTime.now();
        String checkIn = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("Check in: " + checkIn);
        //get employee id
        System.out.println("Nhap ma nhan vien: ");
        String employeeId = sc.nextLine();
        //get employee id
        Employee employee = new Employee();
        employee.readFile();
        while (employee.findById(employeeId) == null) {
            System.out.println("Ma nhan vien khong ton tai, moi nhap lai");
            employeeId = sc.nextLine();
        }
        //get shift id
        System.out.println("Nhap ma ca lam viec: ");
        String shiftId = sc.nextLine();
        //validate shift id
        Shift shift = new Shift();
        shift.readFile();
        while (shift.findById(shiftId) == null) {
            System.out.println("Ma ca lam viec khong ton tai, moi nhap lai");
            shiftId = sc.nextLine();
        }
        //validate check in time
        while (checkIn.compareTo(shift.findById(shiftId).getStartTime()) < 0) {
            //leave out
            System.out.println("Chua den gio lam, ban co muon thoat khong? (Y/N)");
            String select = sc.nextLine();
            if (select.equals("Y")) {
                return;
            } else if (select.equals("N")) {
                System.out.println("Nhap lai gio lam");
                checkIn = sc.nextLine();
            } else {
                System.out.println("Nhap sai, moi nhap lai");
            }
        }
        //validate have check in in that day
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getEmployeeId().equals(employeeId) && timeKeeping.getDate().equals(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                System.out.println("Nhan vien da check in trong ngay nay");
                return;
            }
        }
        //get date
        String date = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        TimeKeeping timeKeeping = new TimeKeeping(this.generateId(), employeeId, shiftId, date, checkIn, "0");
        timeKeepingList.add(timeKeeping);
        //write to file
        this.writeFile();
    }

    //check out
    public void checkOut() {
        //get current time
        LocalDateTime now = LocalDateTime.now();
        String checkOut = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("Check out: " + checkOut);
        //get time keeping id
        System.out.println("Nhap ma cham cong: ");
        String id = sc.nextLine();
        //validate time keeping id
        while (this.findById(id) == null) {
            System.out.println("Ma cham cong khong ton tai, moi nhap lai");
            id = sc.nextLine();
        }
        //set checkout to end time of shift if checkout time is greater than end time of shift
        Shift shift = new Shift();
        shift.readFile();
        if (checkOut.compareTo(shift.findById(this.findById(id).getShiftId()).getEndTime()) > 0) {
            checkOut = shift.findById(this.findById(id).getShiftId()).getEndTime();
        }
        //update check out
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getId().equals(id)) {
                timeKeeping.setCheckOut(checkOut);
            }
        }
        this.writeFile();
    }

    private TimeKeeping findByEmployeeId(String employeeId) {
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getEmployeeId().equals(employeeId)) {
                return timeKeeping;
            }
        }
        return null;
    }

    //show time keeping
    public void showTimeKeeping() {
        System.out.println("||=============Danh sach cham cong============||");
        for (TimeKeeping timeKeeping : timeKeepingList) {
            this.printTimeKeeping(timeKeeping);
            System.out.println("==============================================");
        }
    }

    //calculate working time when check out
    public int calculateWorkingTime(String checkIn, String checkOut) {
        int workingTime = 0;
        if (checkOut.isEmpty()) {
            return workingTime;
        }
        String[] checkInTime = checkIn.split(":");
        String[] checkOutTime = checkOut.split(":");
        int checkInHour = Integer.parseInt(checkInTime[0]);
        int checkInMinute = Integer.parseInt(checkInTime[1]);
        int checkOutHour = Integer.parseInt(checkOutTime[0]);
        int checkOutMinute = Integer.parseInt(checkOutTime[1]);
        if (checkOutHour > checkInHour) {
            workingTime = (checkOutHour - checkInHour) * 60 + (checkOutMinute - checkInMinute);
        } else if (checkOutHour == checkInHour) {
            workingTime = checkOutMinute - checkInMinute;
        }
        return workingTime;
    }

    private void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter("TimeKeeping.txt");
            for (TimeKeeping timeKeeping : timeKeepingList) {
                fileWriter.write(timeKeeping.getId() + "|"
                        + timeKeeping.getEmployeeId() + "|"
                        + timeKeeping.getShiftId() + "|"
                        + timeKeeping.getDate() + "|"
                        + timeKeeping.getCheckIn() + "|"
                        + timeKeeping.getCheckOut());
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
            FileReader fileReader = new FileReader("TimeKeeping.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] timeKeepingArray = line.split("\\|");
                TimeKeeping timeKeeping = new TimeKeeping(timeKeepingArray[0], timeKeepingArray[1], timeKeepingArray[2],
                        timeKeepingArray[3], timeKeepingArray[4], timeKeepingArray[5]);
                timeKeepingList.add(timeKeeping);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Loi doc file");
        }

    }

    private void printTimeKeeping(TimeKeeping timeKeeping) {
        //menu check in and check out
        System.out.println("||=================Thong tin cham cong=================||");
        System.out.println("||Ma cham cong: " + timeKeeping.getId());
        System.out.println("||Ma nhan vien: " + timeKeeping.getEmployeeId());
        System.out.println("||Ma ca lam viec: " + timeKeeping.getShiftId());
        System.out.println("||Ngay cham cong: " + timeKeeping.getDate());
        System.out.println("||Gio vao: " + timeKeeping.getCheckIn());
        System.out.println("||Gio ra: " + timeKeeping.getCheckOut());
        System.out.println("||=====================================================||");
    }


}
