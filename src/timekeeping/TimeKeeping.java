package timekeeping;

import java.util.ArrayList;
import java.util.List;

public class TimeKeeping  {
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

}
