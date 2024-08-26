package za.ac.cput.dto;

import java.util.Objects;

public class SignInRequest {

    private Long employeeId;
    private String password;

    protected SignInRequest() {
    }

    private SignInRequest(Builder builder) {
        this.employeeId = builder.employeeId;
        this.password = builder.password;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInRequest that = (SignInRequest) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, password);
    }

    @Override
    public String toString() {
        return "SignInRequest{" +
                "employeeId=" + employeeId +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {

        private Long employeeId;
        private String password;

        public Builder setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public SignInRequest build(){
            return new SignInRequest(this);
        }
    }
}
