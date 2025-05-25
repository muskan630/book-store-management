package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Check if user already exists
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists!");
        }

        // Save new user
        return userRepository.save(user);
    }

    public void registerUser(String username, String email, String password) {
        User user = new User(username, email, password);
        userRepository.save(user);
    }
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Successful login
        }
        return null; // Invalid credentials
    }
}