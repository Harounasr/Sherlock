package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;

public interface NotVerifiedUserRepository {

    void insertNotVerifiedUser(User user);

    void deleteNotVerifiedUser(String username);

}
