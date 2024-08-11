package za.ac.cput.factory;

import za.ac.cput.domain.Accessory;
import za.ac.cput.domain.Phone;
import za.ac.cput.util.Helper;

public class AccessoryFactory {

    public static Accessory buildAccessory(long accessoryID, String name, String description, Phone phone) {
        if (Helper.isNullorEmpty(accessoryID) || Helper.isNullorEmpty(name) || Helper.isNullorEmpty(description)) {
            return null;
        }
        if (phone == null) {
            return null;
        }

        return new Accessory.Builder()
                .setAccessoryID(accessoryID)
                .setName(name)
                .setDescription(description)
                .setPhone(phone)
                .build();
    }
}
