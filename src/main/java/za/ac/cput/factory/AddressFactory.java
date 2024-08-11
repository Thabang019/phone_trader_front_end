package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.util.Helper;

public class AddressFactory {
    public static Address buildAddress(String number, String streetName, String suburb, String city, String postalCode){
        if(Helper.isNullorEmpty(number) || Helper.isNullorEmpty(streetName) ||
           Helper.isNullorEmpty(suburb) || Helper.isNullorEmpty(city) ||
           Helper.isNullorEmpty(postalCode))
            return null;

        return new Address.Builder()
                .setNumber(number)
                .setStreet(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setPostalCode(postalCode)
                .buildAddress();
    }
}
