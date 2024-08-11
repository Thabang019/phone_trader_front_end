package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Employee;
import za.ac.cput.domain.Employee.Role;
import za.ac.cput.util.Helper;

public class EmployeeFactory {

    public static Employee buildEmployee(long employeeID, String firstName, String middleName, String lastName, String password, Contact contact, Role role) {

        if (Helper.isNullorEmpty(employeeID) || Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(middleName) || Helper.isNullorEmpty(lastName) || Helper.isNullorEmpty(password)) {
            return null;
        }

        if (contact == null || role == null) {
            return null;
        }

        return new Employee.Builder()
                .setEmployeeID(employeeID)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setLastName(lastName)
                .setPassword(password)
                .setContact(contact)
                .setRole(role)
                .build();
    }
}
