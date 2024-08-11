package za.ac.cput.domain;

import java.util.Objects;

public class Phone {

    private Long imei;

    private String brand;

    private String model;

    private String color;

    private double price;

    private String status;

    private Specification specification;

    public Condition condition;

    protected Phone() {

    }

    public enum Condition {
        NEW, USED, REFURBISHED
    }

    private Phone(Phone.Builder builder) {
        this.imei = builder.imei;
        this.brand = builder.brand;
        this.model = builder.model;
        this.color = builder.color;
        this.price = builder.price;
        this.status = builder.status;
        this.specification = builder.specification;
        this.condition = builder.condition;
    }

    public Long getImei() {
        return imei;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public Specification getSpecification() {
        return specification;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;
        return Double.compare(getPrice(), phone.getPrice()) == 0 && Objects.equals(getImei(), phone.getImei()) && Objects.equals(getBrand(), phone.getBrand()) && Objects.equals(getModel(), phone.getModel()) && Objects.equals(getColor(), phone.getColor()) && Objects.equals(getStatus(), phone.getStatus()) && Objects.equals(getSpecification(), phone.getSpecification()) && getCondition() == phone.getCondition();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImei(), getBrand(), getModel(), getColor(), getPrice(), getStatus(), getSpecification(), getCondition());
    }

    @Override
    public String toString() {
        return "Phone{" +
                "imei=" + imei +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", specification=" + specification +
                ", condition=" + condition +
                '}';
    }


    public static class Builder {
        private long imei;
        private String brand;
        private String model;
        private String color;
        private double price;
        private String status;
        private Specification specification;
        private Condition condition;


        public Builder setImei(long imei) {
            this.imei = imei;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Phone.Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setSpecification(Specification specification) {
            this.specification = specification;
            return this;
        }

        public Builder setCondition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder copy(Phone phone) {
            this.imei = phone.imei;
            this.brand = phone.brand;
            this.model = phone.model;
            this.color = phone.color;
            this.price = phone.price;
            this.status = phone.status;
            this.specification = phone.specification;
            this.condition = phone.condition;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }


    }


}
