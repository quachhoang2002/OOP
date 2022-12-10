package base;

import java.util.Scanner;
import java.util.UUID;

abstract public class SystemService implements IAction {
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
        String UID = $type + "-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return UID;
    }

    //validate Time format HH:mm
    public static boolean validateTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }


    //split time to hour and minute
    public static String[] splitTime(String time) {
        return time.split(":");
    }

    //check empty input

}
