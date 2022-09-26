package shift;

import java.util.List;

public interface ITimeKeeping {
    public String getId();
    public void setId(String id);
    public String getEmployeeId();
    public void setEmployeeId(String employeeId);
    public String getShiftId();
    public void setShiftId(String shiftId);
    public String getDate();
    public void setDate(String date);
    public String getCheckIn();
    public void setCheckIn(String checkIn);
    public String getCheckOut();
    public void setCheckOut(String checkOut);
    public TimeKeeping findById(String id);
    public List<TimeKeeping> getTimeKeepingList();
}
