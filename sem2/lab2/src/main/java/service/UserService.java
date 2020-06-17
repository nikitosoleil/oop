package service;

import dao.UserDAO;
import entity.User;
import lombok.RequiredArgsConstructor;
import util.BlockedUserException;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public User login(String email, String password) throws BlockedUserException {
        return userDAO.findUserByLogin(email, password);
    }

    public List<User> findAllUser() {
        return userDAO.findAllUsers();
    }

    public void block(Long userId) {
        userDAO.updateUserBlock(userId, true);
    }

    public void unblock(Long userId) {
        userDAO.updateUserBlock(userId, false);
    }

    public void addUser(User user) {
        userDAO.save(user);
    }
}
