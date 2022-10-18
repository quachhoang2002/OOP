package base;

import java.util.Scanner;
import java.util.UUID;

abstract public class BaseService {
    protected static final String DELIMITER = "|";
    protected static final String SPLIT_PATTERN = "\\|";
    public Scanner sc = new Scanner(System.in);

    //sleep 1s
    public void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //is number pattern
    public boolean isNumber(String str) {
        return str.matches("[0-9]+");
    }

    //gererate id
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    //validate Time format HH:mm
    public boolean validateTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }

    //split time to hour and minute
    public String[] splitTime(String time) {
        return time.split(":");
    }

}
