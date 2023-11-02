package de.ssherlock.business.service;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.repository.UserRepository;

public class UserService {

    public static void main(String[] args) {
        System.out.println("u");
    }

    public static User getUser(String username) {
        try {
            return UserRepository.get(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
