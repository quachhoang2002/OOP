import employee.Department;
import employee.Employee;
import shift.Shift;
import shift.TimeKeeping;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
                    Department department = new Department();
                    department.showMenu();
                    break;
                case "2":
                    Employee employee = new Employee();
                    employee.showMenu();
                    break;
                case "3":
                    Shift shift = new Shift();
                    shift.showMenu();
                    break;
                case "4":
                    TimeKeeping timeKeeping = new TimeKeeping();
                    timeKeeping.showMenu();
                    break;
                case "5":
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