package shift;

import base.BaseInterface;

import java.util.List;

public interface IOnleave  {
    public String getId();
    public void setId(String id);
    public String getEmployeeId();
    public void setEmployeeId(String employeeId);
    public String getShiftId();
    public void setShiftId(String shiftId);
    public String getDate();
    public void setDate(String date);
    public String getReason();
    public void setReason(String reason);
    public Onleave findById(String id);
    public List<Onleave> getOnleaveList();

}
