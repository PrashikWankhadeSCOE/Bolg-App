package com.scalar.blogapp.users;

import com.scalar.blogapp.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private ModelMapper modelMapper;

    public UsersService(UsersRepository usersRepository,ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }
    public UserEntity createUser(CreateUserRequest u){
        UserEntity newUser = modelMapper.map(u,UserEntity.class);

        // Todo : encrypt password and save it

        //-------- No need to write below code model mapper can alone do it by itself ---------
//        var newUser = UserEntity.builder()
//                .username(u.getUsername())
// //               .password(password) //Todo:  Encrypt Password
//                .email(u.getEmail())
//                .build();

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
