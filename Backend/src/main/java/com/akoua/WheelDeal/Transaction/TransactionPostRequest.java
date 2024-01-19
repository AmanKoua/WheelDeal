package com.akoua.WheelDeal.Transaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionPostRequest {

    public Long ownerVehicleId;
    public Long swapperVehicleId;
    public String ownerEmail;
    public Double swapperPriceOffer;
    public String swapperLocationOffer;

    public boolean areFieldsValid(){
        return ownerVehicleId > 0 && swapperVehicleId > 0 && ownerEmail != null && !ownerEmail.isEmpty() && swapperPriceOffer != null & swapperLocationOffer != null && !swapperLocationOffer.isEmpty();
    }

}
