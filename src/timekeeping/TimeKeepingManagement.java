package timekeeping;

import base.SystemService;
import employee.EmployeeManagement;
import shift.ShiftManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeKeepingManagement extends SystemService {
    //attribute
    private static final String FILE_PATH = "timeKeeping.txt";
    private static List<TimeKeeping> timeKeepingList = new ArrayList<>();

    //constructor

    //implement method
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

    public TimeKeeping findByDate(String date) {
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getDate().equals(date)) {
                return timeKeeping;
            }
        }
        return null;
    }

    public TimeKeeping findByEmployeeId(String employeeId) {
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getEmployeeId().equals(employeeId)) {
                return timeKeeping;
            }
        }
        return null;
    }

    public TimeKeeping findByShiftId(String shiftId) {
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getShiftId().equals(shiftId)) {
                return timeKeeping;
            }
        }
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
                    this.show();
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
        EmployeeManagement employee = new EmployeeManagement();
        employee.readFile();
        while (employee.findById(employeeId) == null) {
            System.out.println("Ma nhan vien khong ton tai, moi nhap lai");
            employeeId = sc.nextLine();
        }
        //get shift id
        System.out.println("Nhap ma ca lam viec: ");
        String shiftId = sc.nextLine();
        //validate shift id
        ShiftManagement shift = new ShiftManagement();
        while (shift.findById(shiftId) == null) {
            System.out.println("Ma ca lam viec khong ton tai, moi nhap lai");
            shiftId = sc.nextLine();
        }
        //validate check in time
        String startTime = shift.findById(shiftId).getStartTime();
        if (!validateCheckInTime(checkIn,startTime)){
            System.out.println("Thoi gian check in khong hop le");
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
        TimeKeeping timeKeeping = new TimeKeeping(this.generateId("CC"), employeeId, shiftId, date, checkIn, "0",0);
        System.out.println("Day la ma cham cong cua ban: " + timeKeeping.getId());
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
        ShiftManagement shift = new ShiftManagement();
        if (checkOut.compareTo(shift.findById(this.findById(id).getShiftId()).getEndTime()) > 0) {
            checkOut = shift.findById(this.findById(id).getShiftId()).getEndTime();
        }
        //update check out
        int calculateWorkingTime = this.calculateWorkingTime(this.findById(id).getCheckIn(), checkOut);
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getId().equals(id)) {
                timeKeeping.setCheckOut(checkOut);
                timeKeeping.setTimeWorking(calculateWorkingTime);
            }
        }
        this.writeFile();
    }

    //show time keeping
    public void show() {
        System.out.println("||=============Danh sach cham cong============||");
        for (TimeKeeping timeKeeping : timeKeepingList) {
            this.printTimeKeeping(timeKeeping);
            System.out.println("==============================================");
        }
    }

    //validate checkin time
    public boolean validateCheckInTime(String checkIn, String startTime) {
        String[] checkInTime = checkIn.split(":");
        String[] startTimeTime = startTime.split(":");
        if (Integer.parseInt(checkInTime[0]) < Integer.parseInt(startTimeTime[0])) {
            return false;
        } else if (Integer.parseInt(checkInTime[0]) == Integer.parseInt(startTimeTime[0])) {
            if (Integer.parseInt(checkInTime[1]) < Integer.parseInt(startTimeTime[1])) {
                return false;
            }
        }
        return true;
    }

    //need to do
    public void add() {

    }

    //edit
    public void edit() {
    }

    //delete
    public void delete() {
    }

    public void search() {
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

    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            for (TimeKeeping timeKeeping : timeKeepingList) {
                fileWriter.append(timeKeeping.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(timeKeeping.getEmployeeId());
                fileWriter.append(DELIMITER);
                fileWriter.append(timeKeeping.getShiftId());
                fileWriter.append(DELIMITER);
                fileWriter.append(timeKeeping.getDate());
                fileWriter.append(DELIMITER);
                fileWriter.append(timeKeeping.getCheckIn());
                fileWriter.append(DELIMITER);
                fileWriter.append(timeKeeping.getCheckOut());
                fileWriter.append(DELIMITER);
                fileWriter.append(String.valueOf(timeKeeping.getTimeWorking()));
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Loi ghi file");
        }

    }

    //read file
    public void readFile() {
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] timeKeepingArray = line.split(SPLIT_PATTERN);
                TimeKeeping timeKeeping = new TimeKeeping(timeKeepingArray[0], timeKeepingArray[1], timeKeepingArray[2],
                        timeKeepingArray[3], timeKeepingArray[4], timeKeepingArray[5],Integer.parseInt((timeKeepingArray[6])));
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
