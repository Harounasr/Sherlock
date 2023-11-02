package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;

import java.util.List;
import java.util.function.Predicate;

public class UserRepositoryPsql extends RepositoryPsql implements UserRepository {

    public UserRepositoryPsql() {
        super();
    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void fetchUser(String username) {

    }

    @Override
    public List<User> fetchUsers(Predicate<User> predicate) {
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
