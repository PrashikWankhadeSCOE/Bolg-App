package com.scalar.blogapp.users;

import com.scalar.blogapp.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    UsersService usersService;

    @Test
    void can_create_users(){
       var user = usersService.createUser(new CreateUserRequest(
               "prashik",
               "pwk111",
               "pwk@gmail.com"));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("prashik",user.getUsername());
    }
}
