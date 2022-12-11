import base.ISystem;
import base.Factory;

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
                case "1" -> {
                    ISystem departmentManagement = factory.getInstance("department");
                    departmentManagement.showMenu();
                }
                case "2" -> {
                    ISystem employeeManagement = factory.getInstance("employee");
                    employeeManagement.showMenu();
                }
                case "3" -> {
                    ISystem shiftManagement = factory.getInstance("shift");
                    shiftManagement.showMenu();
                }
                case "4" -> {
                    ISystem timeKeepingManagement = factory.getInstance("timekeeping");
                    timeKeepingManagement.showMenu();
                }
                case "5" -> {
                    ISystem onleaveManagement = factory.getInstance("onleave");
                    onleaveManagement.showMenu();
                }
                case "6" -> {
                    ISystem payrollManagement = factory.getInstance("payroll");
                    payrollManagement.showMenu();
                }
                case "0" -> System.out.println("0=BAN DA THOAT CHUONG TRINH");
                default -> System.out.println("Nhap sai lua chon, xin nhap lai !!!");
            }
        } while (!select.equals("0"));
    }
}