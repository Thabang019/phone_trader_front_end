package za.ac.cput.domain;

import java.util.Objects;

public class Employee {

    private long employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private Contact contact;
    private Role role;
    public Employee() {}
    public enum Role {
        Manager,
        Salesperson,
        Buyer,
    }

    private Employee(Builder builder) {
        this.employeeID = builder.employeeID;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.contact = builder.contact;
        this.role = builder.role;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Contact getContact() {
        return contact;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return employeeID == employee.employeeID &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(middleName, employee.middleName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(contact, employee.contact) &&
                role == employee.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, firstName, middleName, lastName, password, contact, role);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", contact=" + contact +
                ", role=" + role +
                '}';
    }



    public static class Builder {
        private long employeeID;
        private String firstName;
        private String middleName;
        private String lastName;
        private String password;
        private Contact contact;
        private Role role;

        public Builder setEmployeeID(long employeeID) {
            this.employeeID = employeeID;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setContact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder copy(Employee e) {
            this.employeeID = e.employeeID;
            this.firstName = e.firstName;
            this.middleName = e.middleName;
            this.lastName = e.lastName;
            this.password = e.password;
            this.contact = e.contact;
            this.role = e.role;
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }
}

