package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.Buyer;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Sale;
import za.ac.cput.util.Helper;

import java.util.List;

public class BuyerFactory {

    public static Buyer createBuyer(String id, String firstName, String middleName, String lastName, Contact contact, List<Sale> sales){

        if(Helper.isNullorEmpty(id)) {
            throw new IllegalArgumentException();
        }

        if(Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(middleName) || Helper.isNullorEmpty(lastName))
            throw new IllegalArgumentException();

        if(contact == null || sales == null)
            throw new IllegalArgumentException();

        return new Buyer.Builder()
                .setIdentityNumber(id)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setLastName(lastName)
                .setContact(contact)
                .setSales(sales)
                .build();
    }

    public static Buyer createBuyer(String id, String firstName, String lastName, Contact contact, List<Sale> sales){

        if(Helper.isNullorEmpty(id)) {
            throw new IllegalArgumentException();
        }

        if(Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(lastName))
            throw new IllegalArgumentException();

        if(contact == null || sales == null)
            throw new IllegalArgumentException();

        return new Buyer.Builder()
                .setIdentityNumber(id)
                .setFirstName(firstName)
                .setMiddleName(" ")
                .setLastName(lastName)
                .setContact(contact)
                .setSales(sales)
                .build();
    }

}
