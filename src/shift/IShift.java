package shift;

import base.BaseInterface;

import java.util.List;

public interface IShift extends BaseInterface {
    public String getId();
    public void setId(String id);
    public String getName();
    public void setName(String name);
    public String getStartTime();
    public void setStartTime(String startTime);
    public String getEndTime();
    public void setEndTime(String endTime);
    public Shift findById(String id);
    public List<Shift> getShiftList();
}

