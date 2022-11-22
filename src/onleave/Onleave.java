package onleave;

import java.util.ArrayList;
import java.util.List;

public class Onleave  {
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

    //getOnleaveList
    public List<Onleave> getOnleaveList() {
        return onleaveList;
    }




}
