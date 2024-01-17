package com.akoua.WheelDeal.Auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestBody {

    public String email;
    public String password;

    public boolean areFieldsValid(){
        return email != null && !email.isEmpty() && email.contains("@") && email.contains(".") && password != null && !password.isEmpty();
    }

}