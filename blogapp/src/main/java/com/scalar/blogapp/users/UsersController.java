package com.scalar.blogapp.users;


import com.scalar.blogapp.common.dtos.ErrorResponse;
import com.scalar.blogapp.security.JWTService;
import com.scalar.blogapp.users.dtos.CreateUserRequest;
import com.scalar.blogapp.users.dtos.UserResponse;
import com.scalar.blogapp.users.dtos.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UsersController(UsersService usersService,ModelMapper modelMapper,JWTService jwtService){
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
        UserEntity savedUser = usersService.createUser(request);
        URI savedUserUri = URI.create("/users" + savedUser.getId());

        var userResponse = modelMapper.map(savedUser, UserResponse.class);

        userResponse.setToken(jwtService.createJWT(savedUser.getId()));

        return ResponseEntity.created(savedUserUri).body(userResponse);

    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UserEntity savedUser = usersService.loginUser(request.getUsername(),request.getPassword());

        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.createJWT(savedUser.getId()));
        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class,
            UsersService.InvalidCredentialException.class
    })
    ResponseEntity<ErrorResponse > handleUserNotFoundException(Exception ex){

        String message;
        HttpStatus status;

        if(ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }else if(ex instanceof UsersService.InvalidCredentialException){
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }
        else
            {
                message = "Something went wrong";
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }


        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return  ResponseEntity.status(status).body(response);

    }
}
