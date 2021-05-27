package services;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User get(long id){
        return userRepository.get(id);
    }

    public List<User> getAll(){
        return userRepository.getAll();
    }

    public void create(User user){
        userRepository.create(user);
    }
}