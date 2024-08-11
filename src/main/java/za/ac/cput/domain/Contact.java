package za.ac.cput.domain;

import java.util.Objects;

public class Contact {

    private String email;
    private String phoneNumber, altPhoneNumber;
    private Address address;

    public Contact() {
    }

    private Contact(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.altPhoneNumber = builder.altPhoneNumber;
        this.address = builder.address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {return email;}

    public String getAltPhoneNumber() { return altPhoneNumber; }

    public Address getAddress() { return address; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(email, contact.email) && Objects.equals(phoneNumber, contact.phoneNumber) && Objects.equals(altPhoneNumber, contact.altPhoneNumber) && Objects.equals(address, contact.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber, altPhoneNumber, address);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", altPhoneNumber='" + altPhoneNumber + '\'' +
                ", address=" + address +
                '}';
    }

    public static class Builder {
        private String phoneNumber;
        private String email;
        private String altPhoneNumber;
        private Address address;

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAltPhoneNumber(String altPhoneNumber) {
            this.altPhoneNumber = altPhoneNumber;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder copy(Contact contact) {
            this.phoneNumber = contact.phoneNumber;
            this.email = contact.email;
            this.altPhoneNumber = contact.altPhoneNumber;
            this.address = contact.address;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}

