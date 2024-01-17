package com.akoua.WheelDeal.Auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestBody {

    public String firstName;
    public String middleName;
    public String lastName;
    public int age;
    public char gender;
    public long cityName;
    public int profileCreationStatus;
    public String phoneNumber;
    public String email;
    public String password;
    public int dealCount;
    public float avgRating;

}
