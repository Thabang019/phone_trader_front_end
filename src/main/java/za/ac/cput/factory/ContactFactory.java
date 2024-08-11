package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.util.Helper;

public class ContactFactory {

    public static Contact createContact(String phoneNumber, String email, String altPhoneNumber, Address address) {
        if (!Helper.isValidEmail(email) || Helper.isNullorEmpty(phoneNumber) ||
                Helper.isObjectNull(address) || Helper.isNullorEmpty(altPhoneNumber))
            return null;

        return new Contact.Builder()
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAltPhoneNumber(altPhoneNumber)
                .setAddress(address)
                .build();
    }
}


