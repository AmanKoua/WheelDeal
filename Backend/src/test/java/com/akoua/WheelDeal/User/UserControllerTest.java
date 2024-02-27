package com.akoua.WheelDeal.User;

import com.akoua.WheelDeal.Auth.AuthService;
import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.Vehicle.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.akoua.WheelDeal.User.SelfProfileSlice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @InjectMocks
    UserController userController;
    @Mock
    UserRepository userRepository;
    @Mock
    CityRepository cityRepository;
    @Mock
    VehicleRepository vehicleRepository;

    @Test
    public void generateProfileSlice(){



    }
}