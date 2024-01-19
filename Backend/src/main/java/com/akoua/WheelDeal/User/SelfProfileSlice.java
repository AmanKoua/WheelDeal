package com.akoua.WheelDeal.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SelfProfileSlice {

    public long id;
    public String firstName;
    public String middleName;
    public String lastName;
    public int age;
    public String gender;
    public String city;
    public String phoneNumber;
    public String email;
    public int dealCount;
    public float avgRating;

    public SelfProfileSlice(User u, String city){
        this.id = u.id;
        this.firstName = u.firstName;
        this.middleName = u.middleName;
        this.lastName = u.lastName;
        this.age = u.age;
        this.gender = u.gender;
        this.city = city;
        this.phoneNumber = u.phoneNumber;
        this.email = u.email;
        this.dealCount = u.dealCount;
        this.avgRating = u.avgRating;
    }

}
