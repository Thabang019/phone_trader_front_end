package za.ac.cput.util;

import java.util.Random;
import java.util.regex.Pattern;

public class Helper {

    public static boolean isNullorEmpty(String s){
        if(s == null || s.isEmpty())
            return true;
        return false;
    }

    public static boolean isNullorEmpty(long s){
        Long longValue = s;
        if(longValue == null)
            return true;

        return false;
    }

    public static boolean isValidEmail(String emailAddress) {

        if(emailAddress == null || emailAddress.isEmpty())
            return true;

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean validPassword(String password) {

        if (password == null || password.isEmpty())
            return false;

        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!*?&]{8,}$";

        return Pattern.compile(regex)
                .matcher(password)
                .matches();
    }

    public static boolean isObjectNull(Object o){
        if (o.equals(null) || o == null){

            return true;
        }
        return false;

    }

    public static int randomNumber(){
        Random rand = new Random();
        int min = 10000000;
        int max = 99999999;
        int randomNumber = rand.nextInt((max - min) + 1) + min;
        return randomNumber;
    }
}