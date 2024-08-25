package za.ac.cput.dto;

public class TokenStorage {
    private static TokenStorage instance;
    private String token;

    private TokenStorage() {}

    public static synchronized TokenStorage getInstance() {
        if (instance == null) {
            instance = new TokenStorage();
        }
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
