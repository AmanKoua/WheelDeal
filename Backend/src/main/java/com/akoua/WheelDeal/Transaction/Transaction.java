package com.akoua.WheelDeal.Transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_generator",
            sequenceName = "transaction_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_generator"
    )
    public Long id;
//    public Boolean areDetailsNew;
    public Boolean areDetailsNewForOwner;
    public Boolean areDetailsNewForSwapper;
    public Integer status;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    public LocalDate dateCreated;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    public LocalDate dateChanged;

    public Boolean isPendingAmendmentAgreement;

    public Long ownerVehicleId;
    public Long swapperVehicleId;
    public String ownerEmail;
    public String swapperEmail;
    public Double ownerPriceOffer;
    public Double swapperPriceOffer;
    public String ownerLocationOffer;
    public String swapperLocationOffer;
    public Boolean doesOwnerAgree;
    public Boolean doesSwapperAgree;
    public Float ownerRating;
    public Float swapperRating;

    public Transaction(
//            Boolean areDetailsNew,
            Boolean areDetailsNewForOwner,
            Boolean areDetailsNewForSwapper,
            LocalDate dateCreated,
            LocalDate dateChanged,
            Boolean isPendingAmendmentAgreement,
            Long ownerVehicleId,
            Long swapperVehicleId,
            String ownerEmail,
            String swapperEmail,
            Double ownerPriceOffer,
            Double swapperPriceOffer,
            String ownerLocationOffer,
            String swapperLocationOffer,
            Boolean doesOwnerAgree,
            Boolean doesSwapperAgree,
            Float ownerRating,
            Float swapperRating
    ){
//        this.areDetailsNew = areDetailsNew;
        this.areDetailsNewForOwner = areDetailsNewForOwner;
        this.areDetailsNewForSwapper = areDetailsNewForSwapper;
        this.status = 1;
        this.dateCreated = dateCreated;
        this.dateChanged = dateChanged;
        this.isPendingAmendmentAgreement = isPendingAmendmentAgreement;
        this.ownerVehicleId = ownerVehicleId;
        this.swapperVehicleId = swapperVehicleId;
        this.ownerEmail = ownerEmail;
        this.swapperEmail = swapperEmail;
        this.ownerPriceOffer = ownerPriceOffer;
        this.swapperPriceOffer = swapperPriceOffer;
        this.ownerLocationOffer = ownerLocationOffer;
        this.swapperLocationOffer = swapperLocationOffer;
        this.doesOwnerAgree = doesOwnerAgree;
        this.doesSwapperAgree = doesSwapperAgree;
        this.ownerRating = ownerRating;
        this.swapperRating = swapperRating;
    }

}
