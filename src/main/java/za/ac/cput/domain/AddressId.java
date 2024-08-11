package za.ac.cput.domain;

import java.io.Serializable;
import java.util.Objects;

public class AddressId implements Serializable {

    private String number;
    private String postalCode;

    public AddressId(){}

    public AddressId(String number, String postalCode) {
        this.number = number;
        this.postalCode = postalCode;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressId addressId = (AddressId) o;
        return Objects.equals(number, addressId.number) && Objects.equals(postalCode, addressId.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, postalCode);
    }

    @Override
    public String toString() {
        return "AddressId{" +
                "number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
