package base;

import java.util.Scanner;
import java.util.UUID;

abstract public class SystemService implements ISystem {
    protected static final String DELIMITER = "|";
    protected static final String SPLIT_PATTERN = "\\|";
    protected Scanner sc = new Scanner(System.in);

    //sleep 1s
    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //is number pattern
    public static boolean isNumber(String str) {
        return str.matches("[0-9]+");
    }

    //gererate id
    public static String generateId(String $type) {
        String UID = UUID.randomUUID().toString().replace("-", "");
        UID = UID + System.currentTimeMillis();
        UID = $type + "-" + UID.substring(9, 15).toUpperCase();
        return UID;
    }

    //validate Time format HH:mm
    public static boolean validateTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }

    public boolean validateStartEndTime(String endTime, String startTime) {
        if (!validateTime(endTime) || !validateTime(startTime)) {
            return false;
        }
        String[] start = SystemService.splitTime(startTime);
        String[] end = SystemService.splitTime(endTime);
        int startHour = Integer.parseInt(start[0]);
        int startMinute = Integer.parseInt(start[1]);
        int endHour = Integer.parseInt(end[0]);
        int endMinute = Integer.parseInt(end[1]);
        if (endHour > startHour) {
            return true;
        } else if (endHour == startHour) {
            if (endMinute > startMinute) {
                return true;
            }
        }
        return false;
    }

    public int calculateWorkingTime(String startTime, String endTime) {
        int workingTime = 0;
        if (endTime.isEmpty() || endTime.equals("0")) {
            return workingTime;
        }
        String[] checkInTime = startTime.split(":");
        String[] checkOutTime = endTime.split(":");
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


    //split time to hour and minute
    public static String[] splitTime(String time) {
        return time.split(":");
    }

    //check empty input

}
