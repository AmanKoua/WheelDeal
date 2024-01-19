package com.akoua.WheelDeal.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // get the count of updates for a specific vehicle across all transactions
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.ownerVehicleId = :ownerVehicleId AND t.areDetailsNew = TRUE")
    public Integer getUpdateCountByVehicle(@Param("ownerVehicleId") Long ownerVehicleId);

}
