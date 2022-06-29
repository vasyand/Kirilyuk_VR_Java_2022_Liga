package application.repository;


import application.model.User;

import java.util.List;

public interface UserRepository {

    User findById(int id);

    List<User> findAll();

    void update(User user);

    void save(User user);

    void delete(int id);

    void deleteAll();
}
