package com.akoua.WheelDeal.Auth;

import com.akoua.WheelDeal.City.CityRepository;
import com.akoua.WheelDeal.User.User;
import com.akoua.WheelDeal.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CityRepository cityRepository;

    @Test
    public void generateJwtOnLogin(){
        LoginRequestBody loginRequestBody = LoginRequestBody.builder()
            .email("lmao@gmail.com")
            .password("abc123")
            .build();

        User user = new User();
        user.id = 1L;
        user.firstName = "Big";
        user.middleName= "haha";
        user.lastName = "lmao";
        user.age = 30;
        user.gender = "M";
        user.cityId = 1l;
        user.phoneNumber = "1234567890";
        user.email = "lmao@gmail.com";
        user.password = "encodedPassword";
        user.dealCount = 0;
        user.avgRating = 0.0f;
        user.totalRating = 0.0f;

        LoginResponse response = new LoginResponse();
        response.token = "encodedJwtToken";
        response.message = "Login Successful!";

        Mockito.when(userRepository.findUserByEmail("lmao@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(authService.authenticate(user, "abc123")).thenReturn("encodedJwtToken");

        ResponseEntity<Object>  temp = authController.login(loginRequestBody);
        LoginResponse test = (LoginResponse) temp.getBody();

        assertNotNull(temp);
        assertEquals(test.message, "Login Successful!");
        assertEquals(test.token, "encodedJwtToken");
    }

    @Test
    public void verifyLoginRequestIsValid(){
        LoginRequestBody loginRequestBody = LoginRequestBody.builder()
                .email("lmaogmail.com")
                .password("abc123")
                .build();

        User user = new User();
        user.id = 1L;
        user.firstName = "Big";
        user.middleName= "haha";
        user.lastName = "lmao";
        user.age = 30;
        user.gender = "M";
        user.cityId = 1l;
        user.phoneNumber = "1234567890";
        user.email = "lmao@gmail.com";
        user.password = "encodedPassword";
        user.dealCount = 0;
        user.avgRating = 0.0f;
        user.totalRating = 0.0f;

        ResponseEntity<Object> temp = authController.login(loginRequestBody);
        LoginResponse test = (LoginResponse) temp.getBody();

        assertNotNull(test);
        assertEquals(test.message, "Email or password fields are null / empty!");
        assertEquals(temp.getStatusCode(), HttpStatusCode.valueOf(400));
    }

    @Test
    public void verifyUserExistsUponLogin(){

        LoginRequestBody loginRequestBody = LoginRequestBody.builder()
                .email("lmao@gmail.com")
                .password("abc123")
                .build();

        Mockito.when(userRepository.findUserByEmail("lmao@gmail.com")).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Object> temp = authController.login(loginRequestBody);
        LoginResponse test = (LoginResponse) temp.getBody();

        assertNotNull(test);
        assertEquals(test.message, "No user found for provided email!");
        assertEquals(temp.getStatusCode(), HttpStatusCode.valueOf(400));

    }

}