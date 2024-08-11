package za.ac.cput.domain;

import java.util.List;
import java.util.Objects;


public class Buyer extends Customer {

    private List<Sale> sales;

    protected Buyer() {
    }

    public List<Sale> getSales() {
        return sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(sales, buyer.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sales);
    }

    @Override
    public String toString() {
        return "Buyer{" +
                " identityNumber=" + identityNumber +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact +
                ", sales=" + sales +
                '}';
    }

    private Buyer(Builder builder) {
        this.identityNumber = builder.identityNumber;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.contact = builder.contact;
        this.sales = builder.sales;
    }

    public  static class Builder {

        private String identityNumber;
        private String firstName;
        private String middleName;
        private String lastName;
        private Contact contact;
        private List<Sale> sales;

        public Builder setIdentityNumber(String identityNumber) {
            this.identityNumber = identityNumber;
            return this;
        }

        public Builder  setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder  setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder  setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder  setContact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Builder setSales(List<Sale> sales) {
            this.sales = sales;
            return this;
        }

        public Builder copy(Buyer buyer) {
            this.identityNumber = buyer.identityNumber;
            this.firstName = buyer.firstName;
            this.middleName = buyer.middleName;
            this.lastName = buyer.lastName;
            this.contact = buyer.contact;
            this.sales = buyer.sales;
            return this;
        }

        public Buyer build(){
            return new Buyer(this);
        }
    }

}
