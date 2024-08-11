package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.Purchase;
import za.ac.cput.domain.Seller;
import za.ac.cput.util.Helper;

import java.util.List;

public class SellerFactory {

    public static Seller createSeller(String id, String firstName, String middleName, String lastName, Contact contact, List<Purchase> purchases){

        if(Helper.isNullorEmpty(id)) {
            throw new IllegalArgumentException();
        }

        if(Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(middleName) || Helper.isNullorEmpty(lastName))
            throw new IllegalArgumentException();

        if(contact == null || purchases == null)
            throw new IllegalArgumentException();

        return new Seller.Builder()
                .setIdentityNumber(id)
                .setFirstName(firstName)
                .setMiddleName(middleName)
                .setLastName(lastName)
                .setContact(contact)
                .setPurchases(purchases)
                .build();
    }

    public static Seller createSeller(String id, String firstName, String lastName, Contact contact, List<Purchase> purchases){

        if(Helper.isNullorEmpty(id)) {
            throw new IllegalArgumentException();
        }

        if(Helper.isNullorEmpty(firstName) || Helper.isNullorEmpty(lastName))
            throw new IllegalArgumentException();

        if(contact == null || purchases == null)
            throw new IllegalArgumentException();

        return new Seller.Builder()
                .setIdentityNumber(id)
                .setFirstName(firstName)
                .setMiddleName(" ")
                .setLastName(lastName)
                .setContact(contact)
                .setPurchases(purchases)
                .build();
    }
}
