import base.IAction;
import base.Factory;
import shift.ShiftManagement;
import timekeeping.TimeKeepingManagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Factory factory = new Factory();
        Scanner sc = new Scanner(System.in);
        String select ;
        do {
            System.out.println("||================== Menu ===================||");
            System.out.println("||1. Phong Bang                              ||");
            System.out.println("||2. Nhan Vien                               ||");
            System.out.println("||3. Ca Lam                                  ||");
            System.out.println("||4. Cham cong                               ||");
            System.out.println("||5. Nghi Phep                               ||");
            System.out.println("||6. Thong ke luong                          ||");
            System.out.println("||0. Thoat chuong trinh                      ||");
            select = sc.nextLine();
            switch (select) {
                case "1":
                    IAction departmentManagement = factory.getInstance("department");
                    departmentManagement.showMenu();
                    break;
                case "2":
                    IAction employeeManagement = factory.getInstance("employee");
                    employeeManagement.showMenu();
                    break;
                case "3":
                    IAction shiftManagement = factory.getInstance("shift");
                    shiftManagement.showMenu();
                    break;
                case "4":
                    IAction timeKeepingManagement = factory.getInstance("timekeeping");
                    timeKeepingManagement.showMenu();
                    break;
                case "5":
                    IAction onleaveManagement = factory.getInstance("onleave");
                    onleaveManagement.showMenu();
                    break;
                case "6":
                    break;
                case "0":
                    System.out.println("0BAN DA THOAT CHUONG TRINH");
                    break;
                default:
                    System.out.println("Nhap sai lua chon, xin nhap lai !!!");
            }
        } while (!select.equals("0"));
    }
}