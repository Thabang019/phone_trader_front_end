package za.ac.cput.factory;

import za.ac.cput.domain.Employee;
import za.ac.cput.domain.Phone;
import za.ac.cput.domain.Purchase;

import java.time.LocalDate;
import java.time.LocalTime;

public class PurchaseFactory {

    public static Purchase createPurchase (LocalDate date, LocalTime time, Employee employee, double buyingPrice,String paymentMethod, Phone phone ){

        return new Purchase.Builder()
                .setDate(date)
                .setTime(time)
                .setEmployee(employee)
                .setBuyingPrice(buyingPrice)
                .setPaymentMethod(paymentMethod)
                .setPhone(phone)
                .build();
    }
}
