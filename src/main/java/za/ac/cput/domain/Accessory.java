package za.ac.cput.domain;

import java.util.Objects;

public class Accessory {

    private long accessoryID;
    private String name;
    private String description;
    private Phone phone;

    public Accessory() {}

    private Accessory(Builder builder) {
        this.accessoryID = builder.accessoryID;
        this.name = builder.name;
        this.description = builder.description;
        this.phone = builder.phone;
    }

    public long getAccessoryID() {
        return accessoryID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Phone getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accessory accessory)) return false;
        return accessoryID == accessory.accessoryID &&
                Objects.equals(name, accessory.name) &&
                Objects.equals(description, accessory.description) &&
                Objects.equals(phone, accessory.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessoryID, name, description, phone);
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "accessoryID=" + accessoryID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phone=" + phone +
                '}';
    }

    public static class Builder {
        private long accessoryID;
        private String name;
        private String description;
        private Phone phone;

        public Builder setAccessoryID(long accessoryID) {
            this.accessoryID = accessoryID;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPhone(Phone phone) {
            this.phone = phone;
            return this;
        }

        public Builder copy(Accessory accessory) {
            this.accessoryID = accessory.accessoryID;
            this.name = accessory.name;
            this.description = accessory.description;
            this.phone = accessory.phone;
            return this;
        }

        public Accessory build() {
            return new Accessory(this);
        }
    }
}
