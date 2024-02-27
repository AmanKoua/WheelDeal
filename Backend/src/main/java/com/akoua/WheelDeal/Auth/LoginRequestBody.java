package com.akoua.WheelDeal.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestBody {

    public String email;
    public String password;

    public boolean areFieldsValid(){
        return email != null && !email.isEmpty() && email.contains("@") && email.contains(".") && password != null && !password.isEmpty();
    }

}