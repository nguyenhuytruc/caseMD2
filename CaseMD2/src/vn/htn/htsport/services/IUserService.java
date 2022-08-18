package vn.htn.htsport.services;

import vn.htn.htsport.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User adminLogin(String username, String password);

    void add(User newUser);

    void update(User newUser);

    boolean existById(int id);

    boolean existByName(String email);

    boolean existByPhone(String phone);

    boolean existByUserName(String userName);

    User findUserById(int id);
}
