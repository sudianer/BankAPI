package repository;

import model.User;
import dao.UserDAO;

import java.util.List;

public class UserRepository {

    private final UserDAO userDAO;

    public UserRepository(UserDAO dao){
        userDAO = dao;
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User get(long id){
        return userDAO.get(id);
    }

    public void update(User user){
        userDAO.update(user);
    }

    public void delete(long id){
        userDAO.delete(id);
    }

    public void create(User user){
        userDAO.create(user);
    }
}