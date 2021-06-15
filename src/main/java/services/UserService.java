package services;

import dao.UserDAO;
import model.User;
import java.util.List;

public class UserService{

    UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User get(long id){
        return userDAO.get(id);
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public void create(User user){
        userDAO.create(user);
    }
}