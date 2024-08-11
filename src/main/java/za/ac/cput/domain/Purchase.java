package za.ac.cput.domain;

import java.time.*;
import java.util.Objects;

public class Purchase {

    private Long purchaseID;

    private LocalDate date;

    private LocalTime time;

    private Employee employee;

    private double buyingPrice;

    private Seller seller;

    private String paymentMethod;

    private Phone phone;

    protected Purchase() {
    }

    private Purchase(Builder builder) {
        this.purchaseID = builder.purchaseID;
        this.date = builder.date;
        this.time = builder.time;
        this.employee = builder.employee;
        this.buyingPrice = builder.buyingPrice;
        this.paymentMethod = builder.paymentMethod;
        this.phone = builder.phone;

    }

    public Long getPurchaseID() {
        return purchaseID;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Employee getEmployee() {
        return employee;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Phone getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return Double.compare(getBuyingPrice(), purchase.getBuyingPrice()) == 0 && Objects.equals(getPurchaseID(), purchase.getPurchaseID()) && Objects.equals(getDate(), purchase.getDate()) && Objects.equals(getTime(), purchase.getTime()) && Objects.equals(getEmployee(), purchase.getEmployee()) && Objects.equals(getPaymentMethod(), purchase.getPaymentMethod()) && Objects.equals(getPhone(), purchase.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPurchaseID(), getDate(), getTime(), getEmployee(), getBuyingPrice(), getPaymentMethod(), getPhone());
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseID=" + purchaseID +
                ", date=" + date +
                ", time=" + time +
                ", employee=" + employee +
                ", buyingPrice=" + buyingPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", phone=" + phone +
                '}';
    }

    public  static class Builder {

        //public Long purchaseID;
        private long purchaseID;
        private LocalDate date;
        private LocalTime time;
        private Employee employee;
        private Double buyingPrice;
        private String paymentMethod;
        private Phone phone;

        public Purchase.Builder setPurchaseID(long purchaseID) {
            this.purchaseID = purchaseID;
            return this;
        }

        public Purchase.Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Purchase.Builder setTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Purchase.Builder setEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Purchase.Builder setBuyingPrice(double buyingPrice) {
            this.buyingPrice = buyingPrice;
            return this;
        }

        public Purchase.Builder setPaymentMethod(String paymentMethod){
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Purchase.Builder setPhone(Phone phone) {
            this.phone = phone;
            return this;
        }

        public Purchase.Builder copy(Purchase purchase) {
            this.purchaseID = purchase.purchaseID;
            this.date = purchase.date;
            this.time = purchase.time;
            this.employee = purchase.employee;
            this.buyingPrice = purchase.buyingPrice;
            this.paymentMethod = purchase.paymentMethod;
            this.phone = purchase.phone;
            return this;
        }

        public Purchase build(){
            return new Purchase(this);
        }
    }
}

