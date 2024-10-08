package com.akoua.WheelDeal.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // get the count of updates for a specific vehicle across all transactions
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.ownerVehicleId = :ownerVehicleId AND t.areDetailsNewForOwner = TRUE")
    public Integer getIncommingUpdateCountByVehicle(@Param("ownerVehicleId") Long ownerVehicleId);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.swapperVehicleId = :swapperVehicleId AND t.areDetailsNewForSwapper = TRUE")
    public Integer getOutgoingUpdateCountByVehicle(@Param("swapperVehicleId") Long swapperVehicleId);

    @Query("SELECT t FROM Transaction t where t.ownerVehicleId = :ownerVehicleId")
    public List<Transaction> getTransactionForVehicleAsOwner(@Param("ownerVehicleId") Long ownerVehicleId);

    @Query("SELECT t FROM Transaction t where t.swapperVehicleId = :swapperVehicleId")
    public List<Transaction> getTransactionForVehicleAsSwapper(@Param("swapperVehicleId") Long swapperVehicleId);

    @Query("UPDATE Transaction t SET t.areDetailsNewForOwner = FALSE WHERE t.ownerVehicleId = :ownerVehicleId")
    @Modifying
    public void markTransactionSeenAsOwner(@Param("ownerVehicleId") Long ownerVehicleId);

    @Query("UPDATE Transaction t SET t.areDetailsNewForSwapper = FALSE WHERE t.swapperVehicleId = :swapperVehicleId")
    @Modifying
    public void markTransactionSeenAsSwapper(@Param("swapperVehicleId") Long swapperVehicleId);

    @Query("DELETE Transaction t WHERE t.ownerEmail = :ownerEmail AND t.id = :id")
    @Modifying
    public void deleteTransaction (@Param("ownerEmail") String ownerEmail, @Param("id") Long id);

    @Query("DELETE Transaction t WHERE t.ownerEmail = :ownerEmail AND t.ownerVehicleId = :ownerVehicleId")
    @Modifying
    public void removeHangingTransactions(@Param("ownerEmail") String ownerEmail, @Param("ownerVehicleId") Long id);

    @Query("UPDATE Transaction t SET t.doesOwnerAgree = TRUE where t.ownerEmail = :ownerEmail AND t.id = :id")
    @Modifying
    public void acceptTransactionAsOwner(@Param("ownerEmail") String ownerEmail, @Param("id") Long id);

    @Query("UPDATE Transaction t SET t.doesSwapperAgree = TRUE where t.swapperEmail = :swapperEmail AND t.id = :id")
    @Modifying
    public void acceptTransactionAsSwapper(@Param("swapperEmail") String swapperEmail, @Param("id") Long id);
}
