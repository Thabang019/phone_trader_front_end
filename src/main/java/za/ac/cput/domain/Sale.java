package za.ac.cput.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


public class Sale {

    private Long salesID;
    private LocalDate date;
    private LocalTime time;

    private Employee employee;
    private Buyer buyer;
    private Phone phone;
    private String paymentMethod;
    private double amountPaid;


    protected Sale(){
    }

    private Sale(Builder builder){
        this.salesID = builder.salesID;
    this.date = builder.date;
    this.time = builder.time;
    this.phone = builder.phone;
    this.employee = builder.employee;
    this.paymentMethod = builder.paymentMethod;
    this.amountPaid = builder.amountPaid;
}

    public Long getSalesID() {return salesID;}

    public LocalDate getDate() {return date;}

    public LocalTime getTime() {return time;}

    public Employee getEmployee() {return employee;}

    public Phone getPhone() {return phone;}

    public String getPaymentMethod() {return paymentMethod;}

    public double getAmountPaid() {return amountPaid;}

    @Override
    public String toString() {
        return "Sales{" +
                "salesID='" + salesID + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", employeeID='" + employee + '\'' +
                ", phoneID='" + phone + '\'' +
                ", paymentMethod= " + paymentMethod + '\'' +
                ", amountPaid=" + amountPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale sale)) return false;
        return Double.compare(amountPaid, sale.amountPaid) == 0 && Objects.equals(salesID, sale.salesID) && Objects.equals(date, sale.date) && Objects.equals(time, sale.time) && Objects.equals(employee, sale.employee) && Objects.equals(phone, sale.phone) && Objects.equals(paymentMethod, sale.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesID, date, time, employee, phone, paymentMethod, amountPaid);
    }

    public static class Builder {
        private Long salesID;
        private LocalDate date;
        private LocalTime time;
        private Employee employee;
        private Phone phone;
        private String paymentMethod;
        private double amountPaid;

        public Builder setSalesID(long salesID) {
            this.salesID = salesID;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder setTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder setEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder setPhone(Phone phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setAmountPaid(double amountPaid) {
            this.amountPaid = amountPaid;
            return this;
        }
public Builder copy(Sale sale) {
    this.salesID = sale.salesID;
    this.date = sale.date;
    this.time = sale.time;
    this.phone = sale.phone;
    this.employee = sale.employee;
    this.paymentMethod = sale.paymentMethod;
    this.amountPaid = sale.amountPaid;
return this;

}
        public Sale build(){return new Sale(this);}

    }

    }
