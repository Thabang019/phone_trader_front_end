package za.ac.cput.factory;

import za.ac.cput.dto.SignInRequest;
import za.ac.cput.util.Helper;

public class SignInRequestFactory {

    public static SignInRequest createSignInRequest(Long employeeId, String password){

        if(employeeId == null || password.isEmpty())
            return null;

        return new SignInRequest.Builder()
                .setEmployeeId(employeeId)
                .setPassword(password)
                .build();
    }
}
