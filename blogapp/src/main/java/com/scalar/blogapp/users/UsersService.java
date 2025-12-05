package com.scalar.blogapp.users;

import com.scalar.blogapp.security.JWTService;
import com.scalar.blogapp.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository,ModelMapper modelMapper,PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public UserEntity createUser(CreateUserRequest u){
        UserEntity newUser = modelMapper.map(u,UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));

        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username){
        return usersRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId){
        return usersRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username,String password){
        var user = usersRepository.findByUsername(username).orElseThrow(()-> new UnableToSendNotificationException(username));

        var passMatcher = passwordEncoder.matches(password,user.getPassword());
        if(!passMatcher) throw new InvalidCredentialException();

        return user;
    }


    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User with username : " + username + " Not found");
        }

        public UserNotFoundException(Long userId){
            super("User with userId : " + userId + " Not found");
        }
    }

    public static class InvalidCredentialException extends IllegalArgumentException{
        public InvalidCredentialException(){
            super("Invalid username or password combination");
        }
    }
}
