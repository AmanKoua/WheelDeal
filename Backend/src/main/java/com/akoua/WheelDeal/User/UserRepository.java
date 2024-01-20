package com.akoua.WheelDeal.User;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("UPDATE User u SET u.avgRating = :avgRating, u.dealCount = u.dealCount + 1, u.totalRating = u.totalRating + :rating WHERE u.email = :email")
    @Modifying
    void updateUserRating(@Param("avgRating") float avgRating, @Param("rating") float rating, @Param("email") String email);

}
