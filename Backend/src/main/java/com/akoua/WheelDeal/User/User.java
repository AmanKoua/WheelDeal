package com.akoua.WheelDeal.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    public char gender;
    public long cityId;
    public int profileCreationStatus;
    public String phoneNumber;
    public String email;
    public String password;
    public int dealCount;
    public float avgRating;

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
