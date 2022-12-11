package statistical;

import Payroll.Payroll;
import Payroll.PayrollManagement;
import department.DepartmentManagement;
import employee.EmployeeManagement;
import onleave.Onleave;
import onleave.OnleaveManagement;
import timekeeping.TimeKeeping;
import timekeeping.TimeKeepingManagement;

import java.util.List;
import java.util.Scanner;

public class Statistical {
    Scanner sc = new Scanner(System.in);
    EmployeeManagement employeeManagement;
    TimeKeepingManagement timeKeepingManagement;
    DepartmentManagement departmentManagement;
    OnleaveManagement onleaveManagement;
    PayrollManagement payrollManagement;

    public Statistical() {
        employeeManagement = new EmployeeManagement();
        timeKeepingManagement = new TimeKeepingManagement();
        departmentManagement = new DepartmentManagement();
        onleaveManagement = new OnleaveManagement();
        payrollManagement = new PayrollManagement();
    }

    public void showMenu() {
        String select;
        do {
            System.out.println("||================== Menu ===================||");
            System.out.println("||1. Thong ke thong tin co ban                     ||");
            System.out.println("||2. Thong ke tong so gio lam viec theo thang      ||");
            System.out.println("||3. Thong ke tong so tien luong theo thang        ||");
            System.out.println("||4. Thong ke tong so ngay nghi phep theo thang     ||");
            System.out.println("||0. Thoat                                         ||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> showDefaul();
                case "2" -> totalWorkingTimeByMonth();
                case "3" -> totalSalaryByMonth();
                case "4" -> totalOnleaveByMonth();
                case "0" -> System.out.println("Thoat chuc nang thong ke");
                default -> {
                    System.out.println("Nhap sai lua chon, xin nhap lai !!!");
                }
            }

        } while (!select.equals("0"));
    }

    public void showDefaul() {
        int totalEmployee = employeeManagement.getEmployeeList().size();
        int totalDepartment = departmentManagement.getDepartmentList().size();
        int totalOnleave = onleaveManagement.getOnleaveList().size();
        int totalShift = timeKeepingManagement.getTimeKeepingList().size();
        System.out.println("Tong so nhan vien: " + totalEmployee);
        System.out.println("Tong so phong ban: " + totalDepartment);
        System.out.println("Tong so nhan vien nghi phep: " + totalOnleave);
        System.out.println("Tong so ca lam viec: " + totalShift);
    }

    public void totalWorkingTimeByMonth() {
        System.out.println("Thong ke tong so gio lam viec theo thang");
        System.out.println("Nhap thang can thong ke: ");
        String month = sc.nextLine();
        while (!month.matches("^[0-9]{2}$")) {
            System.out.println("Nhap sai dinh dang, xin nhap lai: ");
            month = sc.nextLine();
        }
        System.out.println("Nhap nam can thong ke: ");
        String year = sc.nextLine();
        int totalWorkingTime = 0;
        for (int i = 0; i <= 31; i++) {
            String date = i + "-" + month + "-" + year;
            List<TimeKeeping> timeKeepingList = timeKeepingManagement.findByDate(date);
            for (TimeKeeping timeKeeping : timeKeepingList) {
                totalWorkingTime += timeKeeping.getTimeWorking();
            }
        }
        System.out.println("Tong so gio lam viec trong thang " + month + " nam " + year + " la: " + totalWorkingTime);
    }

    public void totalSalaryByMonth() {
        System.out.println("Thong ke tong so tien luong theo thang");
        System.out.println("Nhap thang muon thong ke: ");
        String month = sc.nextLine();
        while (!month.matches("^[0-9]{2}$")) {
            System.out.println("Nhap sai thang, xin nhap lai: ");
            month = sc.nextLine();
        }
        System.out.println("Nhap nam muon thong ke: ");
        String year = sc.nextLine();
        String date = month + "-" + year;
        List<Payroll> payrollList = payrollManagement.findByDate(date);
        if (payrollList.isEmpty()) {
            System.out.println("Khong co thong tin luong trong thang " + month + " nam " + year);
        } else {
            int totalSalary = 0;
            for (Payroll payroll : payrollList) {
                totalSalary += payroll.getTotalSalary();
            }
            System.out.println("Tong so tien luong trong thang " + month + " nam " + year + " la: " + totalSalary);
        }
    }

    public void totalOnleaveByMonth() {
        System.out.println("Thong ke tong so gio lam viec theo thang");
        System.out.println("Nhap thang can thong ke: ");
        String month = sc.nextLine();
        while (!month.matches("^[0-9]{2}$")) {
            System.out.println("Nhap sai dinh dang, xin nhap lai: ");
            month = sc.nextLine();
        }
        System.out.println("Nhap nam can thong ke: ");
        String year = sc.nextLine();
        int totalOnleave = 0;
        for (int i = 0; i <= 31; i++) {
            String date = i + "-" + month + "-" + year;
            List<Onleave> onleaveList = onleaveManagement.findByDate(date);
            for (Onleave onleave : onleaveList) {
                totalOnleave += 1;
            }
        }
        System.out.println("Tong so ngay nghi phep trong thang " + month + " nam " + year + " la: " + totalOnleave);
    }

}
