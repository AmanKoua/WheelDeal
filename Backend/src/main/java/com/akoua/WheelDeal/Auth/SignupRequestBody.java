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
    public String gender;
    public String cityName;
    public String phoneNumber;
    public String email;
    public String password;

    public void printAllFields(){

        System.out.println("First Name : " + firstName);
        System.out.println("Middle Name : " + middleName);
        System.out.println("Last Name : " + lastName);
        System.out.println("age  : " + age);
        System.out.println("Gender : " + gender);
        System.out.println("City Name : " + cityName);
        System.out.println("Phone number : " + phoneNumber);
        System.out.println("email : " + email);
        System.out.println("password : " + password);

    }

    public boolean areFieldsValid (){

        if(
            firstName == null ||
            firstName.isEmpty() ||
            lastName == null ||
            lastName.isEmpty() ||
            age == 0 ||
            (gender != null && !gender.isEmpty() && !isGenderValid(gender)) ||
            cityName == null ||
            cityName.isEmpty() ||
            phoneNumber == null ||
            phoneNumber.length() != 10 ||
            email == null ||
            email.isEmpty() ||
            !email.contains("@") ||
            !email.contains(".") ||
            password == null ||
            password.isEmpty()
        )
        {
            return false;
        }
        else{
            return true;
        }

    }

    private boolean isGenderValid(String gender){
        return gender.equals("M") || gender.equals("F");
    }

}
