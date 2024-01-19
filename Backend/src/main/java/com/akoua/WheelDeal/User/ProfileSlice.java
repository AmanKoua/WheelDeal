package com.akoua.WheelDeal.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileSlice {
    public long id;
    public String firstName;
    public String middleName;
    public String lastName;
    public long cityId;
    public String phoneNumber;
    public String email;
    public int dealCount;
    public float avgRating;

    public ProfileSlice(User u){
        id = u.id;
        firstName = u.firstName;
        middleName = u.middleName;
        lastName = u.lastName;
        cityId = u.cityId;
        phoneNumber = u.phoneNumber;
        email = u.email;
        dealCount = u.dealCount;
        avgRating = u.avgRating;
    }

}
