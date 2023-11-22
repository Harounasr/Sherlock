package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.User;

import java.sql.Connection;

public class NotVerifiedUserRepositoryPsql extends RepositoryPsql implements NotVerifiedUserRepository {

    /**
     * {@inheritDoc}
     */
    public NotVerifiedUserRepositoryPsql(Connection connection) {
        super(connection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertNotVerifiedUser(User user) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNotVerifiedUser(String username) {

    }
}
