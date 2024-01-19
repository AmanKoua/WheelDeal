package com.akoua.WheelDeal.Vehicle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class PostVehicleRequestBody {
    public String category;
    public String make;
    public String model;
    public int year;
    public String color;
    public double defaultPrice;
    public int condition;

    public boolean isValid(){
        return category != null &&
                !category.isEmpty() &&
                isValidCategory(category) &&
                make != null &&
                !make.isEmpty() &&
                model != null &&
                !model.isEmpty() &&
                year != 0 &&
                year <= 2024 &&
                color != null &&
                !color.isEmpty() &&
                defaultPrice != 0.0D &&
                condition >= 1 &&
                condition <= 5;
    }

    private boolean isValidCategory(String category){
        return Objects.equals(category, "car") || Objects.equals(category, "truck") || Objects.equals(category, "motorcycle");
    }

    public void printAllFields(){
        System.out.println("category : " + category);
        System.out.println("make : " + make);
        System.out.println("model : " + model);
        System.out.println("year : " + year);
        System.out.println("color : " + color);
        System.out.println("defaultPrice : " + defaultPrice);
        System.out.println("condition : " + condition);
    }

}
