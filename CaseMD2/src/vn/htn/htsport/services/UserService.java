package vn.htn.htsport.services;

import vn.htn.htsport.model.Role;
import vn.htn.htsport.model.User;
import vn.htn.htsport.utils.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService{
    public final static String path = "data/users.csv";

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if(instance == null)
            instance = new UserService();
        return instance;
    }


    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<String> records = CSVUtils.read(path);
        for (String record : records) {
            users.add(User.parseUser(record));
        }
        return users;
    }

    @Override
    public User adminLogin(String username, String password) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)
                    && user.getRole().equals(Role.ADMIN)) {
                return user;
            }else if ((user.getUsername().equals(username) && user.getPassword().equals(password)
                    && user.getRole().equals(Role.USER))){
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User newUser) {
        List<User> users = getUsers();
        users.add(newUser);
        CSVUtils.write(path, users);
    }

    @Override
    public void update(User newUser) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                String name = user.getName();
                if (name != null && !name.isEmpty())
                    user.setName(newUser.getName());
                String phone = newUser.getPhone();
                if (phone != null && !phone.isEmpty())
                    user.setPhone(newUser.getPhone());
                String address = newUser.getAddress();
                if (address != null && !address.isEmpty())
                    user.setAddress(newUser.getAddress());
                CSVUtils.write(path, users);
                break;
            }
        }
    }

    @Override
    public boolean existById(int id) {
        return findUserById(id) != null;
    }

    @Override
    public boolean existByName(String email) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    @Override
    public boolean existByPhone(String phone) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getPhone().equals(phone))
                return true;
        }
        return false;
    }

    @Override
    public boolean existByUserName(String userName) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(userName))
                return true;
        }
        return false;
    }

    @Override
    public User findUserById(int id) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }
}
