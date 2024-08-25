package za.ac.cput.dto;

import java.util.Objects;

public class JwtAuthenticationResponse {

    private String token;

    protected JwtAuthenticationResponse(){

    }

    private JwtAuthenticationResponse(Builder builder) {
        this.token = builder.token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtAuthenticationResponse that = (JwtAuthenticationResponse) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        return "JwtAuthenticationResponse{" +
                "token='" + token + '\'' +
                '}';
    }

    public static class Builder {
        private String token;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public JwtAuthenticationResponse build(){
            return new JwtAuthenticationResponse(this);
        }
    }
}