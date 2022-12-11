package Payroll;

import java.util.ArrayList;
import java.util.List;

import base.SystemService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import employee.Employee;
import employee.EmployeeManagement;
import onleave.Onleave;
import onleave.OnleaveManagement;
import shift.Shift;
import shift.ShiftManagement;
import timekeeping.TimeKeeping;
import timekeeping.TimeKeepingManagement;

public class PayrollManagement extends SystemService {
    private static final String PATH_OF_FILE = "Payroll.txt";
    //attribute
    protected static List<Payroll> payrollList = new ArrayList<>();
    protected OnleaveManagement onleaveManagement;
    protected EmployeeManagement employeeManagement;
    protected TimeKeepingManagement timeKeepingManagement;
    protected ShiftManagement shiftManagement;

    public PayrollManagement() {
        this.readFile();
        this.onleaveManagement = new OnleaveManagement();
        this.employeeManagement = new EmployeeManagement();
        this.timeKeepingManagement = new TimeKeepingManagement();
        this.shiftManagement = new ShiftManagement();
    }

    @Override
    public void add() {
        List<Employee> employeeList = employeeManagement.getEmployeeList();
        List<TimeKeeping> timeKeepingList = timeKeepingManagement.getTimeKeepingList();
        List<Onleave> onleaveList = onleaveManagement.getOnleaveList();
        if ((employeeList.size() == 0) || (timeKeepingList.size() == 0) || (onleaveList.size() == 0)) {
            System.out.println("Khong co du lieu de tinh luong");
            return;
        }
        String date = "";
        try {
            System.out.println("Chon Thang muon tinh luong: ");
            String month = sc.nextLine();
            if (Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1) {
                System.out.println("Thang khong hop le");
                return;
            }
            System.out.println("Chon Nam muon tinh luong: ");
            String year = sc.nextLine();
            date = month + "-" + year;
        } catch (Exception e) {
            System.out.println("Nhap sai dinh dang");
            return;
        }
        for (Employee employee : employeeList) {
            float totalSalary = 0;
            String id = SystemService.generateId("PL");
            totalSalary = calculatorSalaryByMonth(timeKeepingList, onleaveList, employee, date);
            Payroll payroll = new Payroll(id, employee.getId(), totalSalary, date);
            payrollList.add(payroll);
        }
        this.writeFile();
    }

    @Override
    public void show() {
        System.out.println("||================= Bang luong =================||");
        for (Payroll payroll : payrollList) {
            this.printPay(payroll);
        }
        System.out.println("||=============================================||");
    }

    @Override
    public void delete() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void search() {

    }

    @Override
    public void showMenu() {
        this.readFile();
        String select;
        do {
            System.out.println("||=============Chuc nang quan ly xem luong ============||");
            System.out.println("||1. Xem Tong Phieu Luong                          ||");
            System.out.println("||2. Tinh luong                                    ||");
            System.out.println("||3. Sua phieu luong                               ||");
            System.out.println("||4. Xoa phieu luong                               ||");
            System.out.println("||5. Tim kiem phieu luong                          ||");
            System.out.println("||0. Thoat                                         ||");
            System.out.println("||====================================================||");
            select = sc.nextLine();
            switch (select) {
                case "1" -> this.show();
                case "2" -> this.add();
                case "3" -> this.edit();
                case "4" -> this.delete();
                case "5" -> this.search();
                case "0" -> System.out.println("Thoat chuong trinh");
                default -> System.out.println("Nhap sai, moi nhap lai");
            }
        }
        while (!select.equals("0"));

    }

    private float calculatorSalaryByMonth(List<TimeKeeping> timeKeepingList, List<Onleave> onleaveList, Employee employee, String date) {
        float totalSalary = 0;
        for (TimeKeeping timeKeeping : timeKeepingList) {
            //format mm-yyyy
            String timeKeepingDateFormat = timeKeeping.getDate().split("-")[1] + "-" + timeKeeping.getDate().split("-")[2];
            if (timeKeeping.getEmployeeId().equals(employee.getId()) && timeKeepingDateFormat.equals(date)) {
                totalSalary += employee.getSalary() * (float) timeKeeping.getTimeWorking() / 60;
            }
        }
        for (Onleave onleave : onleaveList) {
            //format mm-yyyy
            String onleaveDateFormat = onleave.getDate().split("-")[1] + "-" + onleave.getDate().split("-")[2];
            if (onleave.getEmployeeId().equals(employee.getId()) && onleaveDateFormat.equals(date)) {
                Shift shift = shiftManagement.findById(onleave.getShiftId());
                totalSalary += (employee.getSalary() * (float) shift.getWorkingTime() / 60) / 2;
            }
        }
        return totalSalary;
    }

    private void printPay(Payroll payroll) {
        Employee employee = employeeManagement.findById(payroll.getEmployeeId());
        if (employee == null){
            System.out.println("Ma phieu luong : " + payroll.getId() + " khong co nhan vien");
            return;
        }
        String employeeName = employee.getName();
        System.out.println("||==================================||");
        System.out.println("||Ma phieu luong: " + payroll.getId());
        System.out.println("||Ten nhan vien: " + employeeName);
        System.out.println("||Thang luong: " + payroll.getDate());
        System.out.println("||Tong tien: " + payroll.getTotalSalary());
        System.out.println("||=================================||");
    }

    @Override
    public void writeFile() {
        try {
            FileWriter fileWriter = new FileWriter(PATH_OF_FILE);
            for (Payroll payroll : payrollList) {
                fileWriter.append(payroll.getId());
                fileWriter.append(DELIMITER);
                fileWriter.append(payroll.getEmployeeId());
                fileWriter.append(DELIMITER);
                fileWriter.append(String.valueOf(payroll.getTotalSalary()));
                fileWriter.append(DELIMITER);
                fileWriter.append(payroll.getDate());
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFile() {
        try {
            FileReader fileReader = new FileReader(PATH_OF_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] data = line.split(SPLIT_PATTERN);
                Payroll payroll = new Payroll(data[0], data[1], Float.parseFloat(data[2]), data[3]);
                payrollList.add(payroll);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Chua co du lieu");
        }

    }

}