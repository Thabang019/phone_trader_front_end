package za.ac.cput.util;

public class ImeiStorage {
    private static ImeiStorage instance;
    private String imei;

    private ImeiStorage() {}

    public static synchronized ImeiStorage getInstance() {
        if (instance == null) {
            instance = new ImeiStorage();
        }
        return instance;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }
}

