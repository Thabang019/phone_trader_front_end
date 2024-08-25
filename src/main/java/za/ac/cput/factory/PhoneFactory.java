package za.ac.cput.factory;

import za.ac.cput.domain.Phone;
import za.ac.cput.domain.Spec;

public class PhoneFactory {

    public static Phone createPhone(long imei, String brand, String model,
                                    String color, double price,
                                    String status, Spec specification,
                                    Phone.Condition condition){

        return new Phone.Builder()
                .setImei(imei)
                .setBrand(brand)
                .setModel(model)
                .setPrice(price)
                .setColor(color)
                .setStatus(status)
                .setSpecification(specification)
                .setCondition(condition)
                .build();
    }
}
