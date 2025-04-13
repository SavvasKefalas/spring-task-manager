package com.kefalas.taskmanager.services;

import com.kefalas.taskmanager.models.User;
import com.kefalas.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Μέθοδος για να επιστρέφει όλους τους χρήστες
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Μέθοδος για να βρίσκει έναν χρήστη από το ID
    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        return user.orElse(null);
    }

    // Μέθοδος για να δημιουργεί έναν νέο χρήστη
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Μέθοδος για να διαγράφει έναν χρήστη
    public boolean deleteUser(int id) {
        if (userRepository.existsById((long) id)) {
            userRepository.deleteById((long) id);
            return true;
        }
        return false;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
