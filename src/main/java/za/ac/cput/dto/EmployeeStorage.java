package za.ac.cput.dto;

import za.ac.cput.domain.Employee;

public class EmployeeStorage {
    private static EmployeeStorage instance;
    private Employee employee;

    private EmployeeStorage() {
    }

    public static EmployeeStorage getInstance() {
        if (instance == null) {
            instance = new EmployeeStorage();
        }
        return instance;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
