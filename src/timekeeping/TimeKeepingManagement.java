package timekeeping;

import base.SystemService;
import employee.Employee;
import employee.EmployeeManagement;
import shift.Shift;
import shift.ShiftManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimeKeepingManagement extends SystemService {
    //attribute
    private static final String FILE_PATH = "timeKeeping.txt";
    protected static List<TimeKeeping> timeKeepingList = new ArrayList<>();
    protected EmployeeManagement employeeManagement;
    protected ShiftManagement shiftManagement;


    public TimeKeepingManagement() {
        if (timeKeepingList.isEmpty()) {
            readFile();
        }
        this.employeeManagement = new EmployeeManagement();
        this.shiftManagement = new ShiftManagement();
    }

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
        return TimeKeepingManagement.timeKeepingList;
    }

    public List<TimeKeeping> findByDate(String date) {
        List<TimeKeeping> timeKeepingList = new ArrayList<>();
        for (TimeKeeping timeKeeping : TimeKeepingManagement.timeKeepingList) {
            if (timeKeeping.getDate().equals(date)) {
                timeKeepingList.add(timeKeeping);
            }
        }
        return timeKeepingList;
    }

    public List<TimeKeeping> findByEmployeeId(String employeeId) {
        List<TimeKeeping> timeKeepings = new ArrayList<>();
        for (TimeKeeping timeKeeping : TimeKeepingManagement.timeKeepingList) {
            if (timeKeeping.getEmployeeId().equals(employeeId)) {
                timeKeepings.add(timeKeeping);
            }
        }
        return timeKeepings;
    }

    public List<TimeKeeping> findByShiftId(String shiftId) {
        List<TimeKeeping> timeKeepings = new ArrayList<>();
        for (TimeKeeping timeKeeping : TimeKeepingManagement.timeKeepingList) {
            if (timeKeeping.getShiftId().equals(shiftId)) {
                timeKeepings.add(timeKeeping);
            }
        }
        return timeKeepings;
    }


    //menu

    public void showMenu() {
        String select;
        do {
            System.out.println("||=============Chuc nang quan ly cham cong============||");
            System.out.println("||1. Check In                               ||");
            System.out.println("||2. Check Out                              ||");
            System.out.println("||3. Xem cham cong                          ||");
            System.out.println("||4. Sua thoi gian cham cong                ||");
            System.out.println("||5. Xoa cham cong                          ||");
            System.out.println("||6. Tim thong tin cham cong                ||");
            System.out.println("||0. Thoat                                  ||");
            System.out.println("||===========================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> this.checkIn();
                case "2" -> this.checkOut();
                case "3" -> this.show();
                case "4" -> this.edit();
                case "5" -> this.delete();
                case "6" -> this.search();
                case "0" -> System.out.println("Thoat chuong trinh");
                default -> System.out.println("Nhap sai, moi nhap lai");
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
        Employee employee = this.employeeManagement.findById(employeeId);
        while (employee == null) {
            System.out.println("Ma nhan vien khong ton tai, moi nhap lai");
            employeeId = sc.nextLine();
            employee = this.employeeManagement.findById(employeeId);
        }
        //get shift id
        System.out.println("Nhap ma ca lam viec: ");
        String shiftId = sc.nextLine();
        //validate shift id
        ShiftManagement shift = this.shiftManagement;
        while (shift.findById(shiftId) == null) {
            System.out.println("Ma ca lam viec khong ton tai, moi nhap lai");
            shiftId = sc.nextLine();
        }
        //validate check in time
        String startTime = shift.findById(shiftId).getStartTime();
        if (!isGreaterCheckTime(checkIn, startTime)) {
            System.out.println("Thoi gian check in khong hop le");
            return;
        }
        String currentDate = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //validate have check in in that day
        for (TimeKeeping timeKeeping : timeKeepingList) {
            if (timeKeeping.getEmployeeId().equals(employeeId) && timeKeeping.getDate().equals(currentDate)) {
                System.out.println("Nhan vien da check in trong ngay nay");
                return;
            }
        }
        String endTime = shift.findById(shiftId).getEndTime();
        if (isGreaterCheckTime(checkIn, endTime)) {
            System.out.println("Nhan vien da check in qua gio");
            return;
        }
        //get date
        TimeKeeping timeKeeping = new TimeKeeping(SystemService.generateId("CC"), employeeId, shiftId, currentDate, checkIn, "0", 0);
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
        TimeKeeping timeKeeping = this.findById(id);
        //validate time keeping id
        while (timeKeeping == null) {
            System.out.println("Ma cham cong khong ton tai, moi nhap lai");
            id = sc.nextLine();
            timeKeeping = this.findById(id);
        }
        //set checkout to end time of shift if checkout time is greater than end time of shift
        Shift shift = this.shiftManagement.findById(timeKeeping.getShiftId());
        if (shift == null) {
            System.out.println("Khong tim thay ca lam viec");
            return;
        }
        if (isGreaterCheckTime(checkOut, shift.getEndTime())) {
            checkOut = shift.getEndTime();
        }
        //update check out
        int calculateWorkingTime = this.calculateWorkingTime(timeKeeping.getCheckIn(), checkOut);
        timeKeeping.setCheckOut(checkOut);
        timeKeeping.setTimeWorking(calculateWorkingTime);
        this.writeFile();
    }

    //show time keeping
    public void show() {
        System.out.println("||=================== Danh sach cham cong ===================||");
        //sort revert time keeping list by working time
        //timeKeepingList.sort((o1, o2) -> o2.getTimeWorking() - o1.getTimeWorking());
        for (TimeKeeping timeKeeping : timeKeepingList) {
            this.printTimeKeeping(timeKeeping);
        }
        System.out.println("========================================================");
    }

    //validate checkin time

    /**
     * @param checkTime
     * @param time
     * @return true if check time is greater than  time
     */
    public boolean isGreaterCheckTime(String checkTime, String time) {
        String[] checkInTime = checkTime.split(":");
        String[] startTimeTime = time.split(":");
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
        System.out.println("Nhap ma cham cong can sua: ");
        String id = sc.nextLine();
        TimeKeeping timeKeeping = this.findById(id);
        if (timeKeeping == null) {
            System.out.println("Ma cham cong khong ton tai");
            return;
        }
        String select;
        do {
            System.out.println("||=================== Sua thong tin cham cong ===================||");
            System.out.println("||1. Sua thoi gian check in                                    ||");
            System.out.println("||2. Sua thoi gian check out                                   ||");
            System.out.println("||0. Thoat                                                     ||");
            System.out.println("||==============================================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    System.out.println("Nhap thoi gian check in: ");
                    String checkIn = sc.nextLine();
                    if (!validateStartEndTime(checkIn, timeKeeping.getCheckOut())) {
                        System.out.println("Thoi gian check in khong hop le");
                        break;
                    }
                    timeKeeping.setCheckIn(checkIn);
                    this.writeFile();
                }
                case "2" -> {
                    System.out.println("Nhap thoi gian check out: ");
                    String checkOut = sc.nextLine();
                    if (!validateStartEndTime(timeKeeping.getCheckIn(), checkOut)) {
                        System.out.println("Thoi gian check out khong hop le");
                        break;
                    }
                    Shift shift = this.shiftManagement.findById(timeKeeping.getShiftId());
                    if (shift == null) {
                        System.out.println("Khong tim thay ca lam viec");
                        break;
                    }
                    if (isGreaterCheckTime(checkOut, shift.getEndTime())) {
                        checkOut = shift.getEndTime();
                    }
                    timeKeeping.setCheckOut(checkOut);
                    this.writeFile();
                }
                case "0" -> System.out.println("Thoat chuc nang sua");
                default -> System.out.println("Nhap sai, moi nhap lai");
            }
        } while (!select.equals("0"));
    }

    //delete
    public void delete() {
        System.out.println("Nhap ma cham cong can xoa: ");
        String id = sc.nextLine();
        TimeKeeping timeKeeping = this.findById(id);
        if (timeKeeping == null) {
            System.out.println("Ma cham cong khong ton tai");
            return;
        }
        timeKeepingList.remove(timeKeeping);
        this.writeFile();
    }

    public void search() {
        String select;
        do {
            System.out.println("||=================== Tim kiem cham cong ===================||");
            System.out.println("||1. Tim kiem theo ma cham cong                             ||");
            System.out.println("||2. Tim kiem theo ma nhan vien                             ||");
            System.out.println("||3. Tim kiem theo ma ca lam viec                           ||");
            System.out.println("||4. Tim kiem theo ngay cham cong                           ||");
            System.out.println("||0. Thoat                                                  ||");
            System.out.println("||==========================================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    System.out.println("Nhap ma cham cong can tim kiem: ");
                    String id = sc.nextLine();
                    TimeKeeping timeKeeping = this.findById(id);
                    this.printTimeKeeping(timeKeeping);
                }
                case "2" -> {
                    System.out.println("Nhap ma nhan vien can tim kiem: ");
                    String employeeId = sc.nextLine();
                    List<TimeKeeping> timeKeepings = this.findByEmployeeId(employeeId);
                    if (timeKeepings.size() == 0) {
                        System.out.println("Khong tim thay thong tin cham cong");
                    }
                    for (TimeKeeping timeKeeping : timeKeepings) {
                        this.printTimeKeeping(timeKeeping);
                    }
                }
                case "3" -> {
                    System.out.println("Nhap ma ca lam viec can tim kiem: ");
                    String shiftId = sc.nextLine();
                    List<TimeKeeping> timeKeepings = this.findByShiftId(shiftId);
                    if (timeKeepings.size() == 0) {
                        System.out.println("Khong tim thay thong tin cham cong");
                    }
                    for (TimeKeeping timeKeeping : timeKeepings) {
                        this.printTimeKeeping(timeKeeping);
                    }
                }
                case "4" -> {
                    String date;
                    try {
                        System.out.println("Nhap ngay can tim kiem: ");
                        String day = sc.nextLine();
                        System.out.println("Nhap thang can tim kiem: ");
                        String month = sc.nextLine();
                        System.out.println("Nhap nam can tim kiem: ");
                        String year = sc.nextLine();
                        date = day + "-" + month + "-" + year;
                        if (!checkDate(date)) {
                            System.out.println("Ngay thang nam khong hop le");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Nhap sai dinh dang ngay thang");
                        break;
                    }
                    List<TimeKeeping> timeKeepings = this.findByDate(date);
                    if (timeKeepings.size() == 0) {
                        System.out.println("Khong tim thay thong tin cham cong");
                    }
                    for (TimeKeeping timeKeeping : timeKeepings) {
                        this.printTimeKeeping(timeKeeping);
                    }
                }
                case "0" -> System.out.println("Thoat chuc nang tim kiem");
                default -> System.out.println("Nhap sai, moi nhap lai");

            }
        } while (!select.equals("0"));
    }


    //calculate working time when check out


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
                        timeKeepingArray[3], timeKeepingArray[4], timeKeepingArray[5], Integer.parseInt((timeKeepingArray[6])));
                timeKeepingList.add(timeKeeping);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Loi doc file");
        }

    }


    private void printTimeKeeping(TimeKeeping timeKeeping) {
        if (timeKeeping == null) {
            System.out.println("Khong tim thay cham cong");
            return;
        }
        Employee employee = this.employeeManagement.findById(timeKeeping.getEmployeeId());
        Shift shift = this.shiftManagement.findById(timeKeeping.getShiftId());
        if (employee == null || shift == null) {
            System.out.println("Ma cham cong " + timeKeeping.getId() + "Khong tim thay nhan vien hoac ca lam viec");
            return;
        }
        //menu check in and check out
        System.out.println("==============================================");
        System.out.println("||Ma cham cong: " + timeKeeping.getId());
        System.out.println("||Ten nhan vien: " + employee.getName());
        System.out.println("||Ten ca lam viec: " + shift.getName());
        System.out.println("||Ngay cham cong: " + timeKeeping.getDate());
        System.out.println("||Gio vao: " + timeKeeping.getCheckIn());
        System.out.println("||Gio ra: " + timeKeeping.getCheckOut());
        System.out.println("||So phut lam viec: " + timeKeeping.getTimeWorking() + " phut");
        System.out.println("==============================================");
    }
}
