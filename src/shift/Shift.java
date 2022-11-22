package shift;


public class Shift  {
    private String id;
    private String name;
    private String startTime;
    private String endTime;

    private String workingTime;

    public Shift() {
        this.id = "";
        this.name = "";
        this.startTime = "";
        this.endTime = "";
        this.workingTime = "";
    }

    public Shift(String id, String name, String startTime, String endTime, String workingTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workingTime = workingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

}


