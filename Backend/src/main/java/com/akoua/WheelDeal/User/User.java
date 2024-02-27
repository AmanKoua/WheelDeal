package com.akoua.WheelDeal.User;

import com.akoua.WheelDeal.Auth.SignupRequestBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Entity
@Table(name="wUser")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
        name="user_id_sequence",
        sequenceName="user_id_sequence",
        allocationSize=1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator="user_id_sequence"
    )
    public long id;
    public String firstName;
    public String middleName;
    public String lastName;
    public int age;
    public String gender;
    public long cityId;
    public String phoneNumber;
    public String email;
    public String password;
    public int dealCount;
    public float avgRating;
    public float totalRating;

    public User(SignupRequestBody request, long cityId, String passwordHash){

        this.firstName = request.firstName;
        this.middleName = request.middleName;
        this.lastName = request.lastName;
        this.age = request.age;
        this.gender = request.gender;
        this.cityId = cityId;
        this.phoneNumber = request.phoneNumber;
        this.email = request.email;
        this.password = passwordHash;
        this.dealCount = 0;
        this.avgRating = 0.0F;
        this.totalRating = 0;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
