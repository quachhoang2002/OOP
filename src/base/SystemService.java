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
    public static String generateId(String $prefix) {
        String UID = UUID.randomUUID().toString().replace("-", "");
        UID = UID + System.currentTimeMillis();
        UID = $prefix + "-" + UID.substring(9, 15).toUpperCase();
        return UID;
    }

    //validate Time format HH:mm
    public static boolean validateTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }

    protected boolean checkDate(String date) {
        String[] dateArr = date.split("-");
        if (dateArr.length != 3) {
            return false;
        }
        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int year = Integer.parseInt(dateArr[2]);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day > 0 && day <= 31) {
                    return true;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 0 && day <= 30) {
                    return true;
                }
                break;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (day > 0 && day <= 29) {
                        return true;
                    }
                } else {
                    if (day > 0 && day <= 28) {
                        return true;
                    }
                }
                break;
        }
        return false;
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

    public boolean isGreaterCheckTime(String checkTime, String time) {
        if (!validateTime(checkTime) || !validateTime(time)) {
            throw new IllegalArgumentException("Invalid time format");
        }
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
