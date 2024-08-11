package za.ac.cput.factory;

import za.ac.cput.domain.Returns;
import za.ac.cput.domain.Sale;

import java.time.LocalDate;

public class ReturnFactory {

        public static Returns createReturn( long returnID, String reasonForReturn, Sale sale, LocalDate returnDate ){

            return new Returns.Builder()
                    .setReasonForReturn(reasonForReturn)
                    .setSale(sale)
                    .setReturnDate(returnDate)
                    .setReturnID(returnID)
                    .build();

        }
}
