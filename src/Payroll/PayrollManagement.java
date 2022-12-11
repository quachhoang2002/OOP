package Payroll;

import base.SystemService;
import employee.Employee;
import employee.EmployeeManagement;
import onleave.Onleave;
import onleave.OnleaveManagement;
import shift.Shift;
import shift.ShiftManagement;
import timekeeping.TimeKeeping;
import timekeeping.TimeKeepingManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayrollManagement extends SystemService {
    private static final String PATH_OF_FILE = "Payroll.txt";
    //attribute
    protected static List<Payroll> payrollList = new ArrayList<>();
    protected OnleaveManagement onleaveManagement;
    protected EmployeeManagement employeeManagement;
    protected TimeKeepingManagement timeKeepingManagement;
    protected ShiftManagement shiftManagement;

    public PayrollManagement() {
        if (payrollList.isEmpty()) {
            readFile();
        }
        this.onleaveManagement = new OnleaveManagement();
        this.employeeManagement = new EmployeeManagement();
        this.timeKeepingManagement = new TimeKeepingManagement();
        this.shiftManagement = new ShiftManagement();
    }

    //region default method
    public Payroll findPayrollById(String id) {
        for (Payroll payroll : payrollList) {
            if (payroll.getId().equals(id)) {
                return payroll;
            }
        }
        return null;
    }

    public List<Payroll> findPayrollByEmployeeId(String employeeId) {
        List<Payroll> payrollList = new ArrayList<>();
        for (Payroll payroll : PayrollManagement.payrollList) {
            if (payroll.getEmployeeId().equals(employeeId)) {
                payrollList.add(payroll);
            }
        }
        return payrollList;
    }

    public List<Payroll> findByDate(String date) {
        List<Payroll> payrollList = new ArrayList<>();
        for (Payroll payroll : PayrollManagement.payrollList) {
            if (payroll.getDate().equals(date)) {
                payrollList.add(payroll);
            }
        }
        return payrollList;
    }

    //endregion
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
            date = this.inputDate();
        } catch (Exception e) {
            System.out.println("Nhap sai dinh dang");
            return;
        }
        if (findByDate(date).size() > 0) {
            System.out.println("Luong da duoc tinh cho thang nay ban muon tinh lai khong? (Y/N)");
            String select;
            do {
                select = sc.nextLine();
                if (select.equals("Y")) {
                    for (Payroll payroll : findByDate(date)) {
                        payrollList.remove(payroll);
                    }
                    break;
                } else if (select.equals("N")) {
                    return;
                } else {
                    System.out.println("Nhap sai, vui long nhap lai");
                }
            } while (true);
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
        System.out.println("Nhap id bang luong muon xoa: ");
        String id = sc.nextLine();
        Payroll payroll = this.findPayrollById(id);
        if (payroll == null) {
            System.out.println("Khong tim thay bang luong");
            return;
        }
        payrollList.remove(payroll);
    }

    @Override
    public void edit() {
        System.out.println("Nhap id bang luong muon sua: ");
        String id = sc.nextLine();
        Payroll payroll = this.findPayrollById(id);
        if (payroll == null) {
            System.out.println("Khong tim thay bang luong");
            return;
        }
        System.out.println("Nhap tien luong moi: ");
        float salary = Float.parseFloat(sc.nextLine());
        payroll.setTotalSalary(salary);
        this.writeFile();
    }

    @Override
    public void search() {
        String select;
        do {
            System.out.println("||================= Tim kiem bang luong =================||");
            System.out.println("|| 1. Tim kiem bang luong theo id                        ||");
            System.out.println("|| 2. Tim kiem bang luong theo id nhan vien              ||");
            System.out.println("|| 3. Tim kiem bang luong theo thang                     ||");
            System.out.println("|| 0. Thoat                                              ||");
            System.out.println("||=======================================================||");
            System.out.println("Nhap lua chon: ");
            select = sc.nextLine();
            switch (select) {
                case "1" -> {
                    System.out.println("Nhap id bang luong muon tim kiem: ");
                    String id = sc.nextLine();
                    Payroll payroll = this.findPayrollById(id);
                    this.printPay(payroll);
                }
                case "2" -> {
                    System.out.println("Nhap id nhan vien: ");
                    String employeeId = sc.nextLine();
                    List<Payroll> payrollList = this.findPayrollByEmployeeId(employeeId);
                    if (payrollList.size() == 0) {
                        System.out.println("Khong tim thay bang luong");
                    }
                    for (Payroll payroll : payrollList) {
                        this.printPay(payroll);
                    }
                }
                case "3" -> {
                    String date;
                    try {
                        date = this.inputDate();
                    } catch (Exception e) {
                        System.out.println("Dinh dang khong hop le");
                        break;
                    }
                    List<Payroll> payrollList1 = this.findByDate(date);
                    if (payrollList1.size() == 0) {
                        System.out.println("Khong tim thay bang luong");
                    }
                    for (Payroll payroll : payrollList1) {
                        this.printPay(payroll);
                    }
                }
                case "0" -> System.out.println("Thoat");
                default -> System.out.println("Lua chon khong hop le");
            }
        } while (!select.equals("0"));

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

    private String inputDate() {
        String date;
        System.out.println("Chon Thang muon tinh luong: ");
        String month = sc.nextLine();
        while (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
            System.out.println("Thang khong hop le, moi nhap lai: ");
            month = sc.nextLine();
        }
        System.out.println("Chon Nam muon tinh luong: ");

        String year = sc.nextLine();
        date = month + "-" + year;
        return date;
    }

    private void printPay(Payroll payroll) {
        if (payroll == null) {
            System.out.println("Khong tim thay bang luong");
            return;
        }
        Employee employee = employeeManagement.findById(payroll.getEmployeeId());
        if (employee == null) {
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