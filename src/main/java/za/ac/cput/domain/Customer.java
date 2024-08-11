package za.ac.cput.domain;

import java.util.Objects;

public class Customer extends Name {

    protected String identityNumber;
    protected Contact contact;
    protected Customer() {
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(identityNumber, customer.identityNumber) && Objects.equals(contact, customer.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), identityNumber, contact);
    }
}
