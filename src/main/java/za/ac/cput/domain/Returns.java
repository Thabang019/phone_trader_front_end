package za.ac.cput.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Returns {
    private long returnID;
    private Sale sale;
    private String reasonForReturn;
    private LocalDate returnDate;

    protected Returns(){}

    private Returns (Builder builder){
        this.returnID = builder.returnID;
        this.sale = builder.sale;
        this.reasonForReturn = builder.reasonForReturn;
        this.returnDate = builder.returnDate;
    }

    public long getReturnID() {return returnID;}
    public Sale getSales() {return sale;}

    public String getReasonForReturn() {return reasonForReturn;}

    public LocalDate getReturnDate() {return returnDate;}

    @Override
    public String toString() {
        return "Returns{" +
                "returnID='" + returnID + '\'' +
                ", sales='" + sale + '\'' +
                ", ReasonForReturn='" + reasonForReturn + '\'' +
                ", ReturnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Returns returns)) return false;
        return Objects.equals(returnID, returns.returnID) && Objects.equals(sale, returns.sale) &&  Objects.equals(reasonForReturn, returns.reasonForReturn) && Objects.equals(returnDate, returns.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnID, reasonForReturn, returnDate);
    }

    public static class Builder {
        private long returnID;
        private Sale sale;
        private String reasonForReturn;
        private LocalDate returnDate;

        public Builder setReturnID(long returnID) {
            this.returnID = returnID;
            return this;
        }
        public Builder setSale(Sale sale) {
            this.sale = sale;
            return this;
        }


        public Builder setReasonForReturn(String reasonForReturn) {
            this.reasonForReturn = reasonForReturn;
            return this;
        }
        public Builder setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }
        public Builder copy(Returns returns) {
            this.returnID = returns.returnID;
            this.sale = returns.sale;
            this.reasonForReturn = returns.reasonForReturn;
            this.returnDate = returns.returnDate;
            return this;
        }
        public Returns build(){return new Returns(this);}
    }
}
