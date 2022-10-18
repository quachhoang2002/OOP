package shift;

import base.BaseInterface;
import shift.Shift;

import java.util.List;

public interface IShiftManagement extends BaseInterface {
    public List<Shift> getShiftList();

    public Shift findById(String id);

    public Shift findByName(String name);

    public Shift findByStartTime(String startTime);

    public Shift findByEndTime(String endTime);

}
