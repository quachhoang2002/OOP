package timekeeping;

import base.BaseInterface;
import timekeeping.TimeKeeping;

import java.util.List;

public interface ITimeKeepingManagement extends BaseInterface {
    public List<TimeKeeping> getTimeKeepingList();

    public TimeKeeping findById(String id);

    public TimeKeeping findByDate(String date);

    public TimeKeeping findByEmployeeId(String employeeId);

    public TimeKeeping findByShiftId(String shiftId);

}


