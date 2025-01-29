package model;

import main.Logable;

public class Employee extends Person implements Logable {

    private int employeeId;
    private String password;

    private final int EMPLOYEE_ID = 123;
    private final String PASSWORD = "test";

    public Employee(int employeeId, String password, String name) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean login(int user, String password) {
        if (user == EMPLOYEE_ID && password.equals(PASSWORD)) {
            System.out.println("Correct Id and password");
            return true;
        }
        System.out.println("Incorrect Id and password");
        return false;
    }

}
