package za.ac.cput.domain;

import java.util.List;
import java.util.Objects;


public class Seller extends Customer {

    private List<Purchase> purchases;

    protected Seller() {
    }

    private Seller(Builder builder) {
        this.identityNumber = builder.identityNumber;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.contact = builder.contact;
        this.purchases = builder.purchases;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(purchases, seller.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), purchases);
    }

    @Override
    public String toString() {
        return "Seller{" +
                ", identityNumber=" + identityNumber +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact +
                "purchases=" + purchases +
                '}';
    }

    public  static class Builder {
        private String identityNumber;
        private String firstName;
        private String middleName;
        private String lastName;
        private Contact contact;
        private List<Purchase> purchases;


        public Builder setIdentityNumber(String identityNumber) {
            this.identityNumber = identityNumber;
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

        public Builder setContact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Builder setPurchases(List<Purchase> purchases) {
            this.purchases = purchases;
            return this;
        }

        public Builder copy(Seller seller) {
            this.identityNumber = seller.identityNumber;
            this.firstName = seller.firstName;
            this.middleName = seller.middleName;
            this.lastName = seller.lastName;
            this.contact = seller.contact;
            this.purchases = seller.purchases;
            return this;
        }

        public Seller build(){
            return new Seller(this);
        }
    }
}
