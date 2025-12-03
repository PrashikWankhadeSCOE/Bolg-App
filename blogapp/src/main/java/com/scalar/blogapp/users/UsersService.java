package com.scalar.blogapp.users;

import com.scalar.blogapp.users.dtos.CreateUserRequest;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public UserEntity createUser(CreateUserRequest u){
        var newUser = UserEntity.builder().username(u.getUsername())
 //               .password(password) //Todo:  Encrypt Password
                .email(u.getEmail())
                .build();

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

        //Todo: match pass  condition to be added
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
}
