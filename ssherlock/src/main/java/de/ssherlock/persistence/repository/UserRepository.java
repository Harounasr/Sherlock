package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;

import java.util.List;
import java.util.function.Predicate;

public interface UserRepository {

    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(String username);
    void fetchUser(String username);
    List<User> fetchUsers(Predicate<User> predicate);
    boolean emailExists(String email);
    boolean userExists(String username);

}
