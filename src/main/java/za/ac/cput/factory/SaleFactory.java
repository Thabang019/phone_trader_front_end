package za.ac.cput.factory;

import za.ac.cput.domain.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class SaleFactory {
    public static Sale createSale(LocalDate date, LocalTime time, Employee employee, Phone phone, String paymentMethod, double amountPaid) {

        return new Sale.Builder()
                .setDate(date)
                .setTime(time)
                .setEmployee(employee)
                .setPhone(phone)
                .setPaymentMethod(paymentMethod)
                .setAmountPaid(amountPaid)
                .build();
    }
}
