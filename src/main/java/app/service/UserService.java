package app.service;

import app.model.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(User user) {
        return userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // public User getUserById(Long id) {
    //     return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
    // }

    // public User createUser(User user) {
    //     return userRepository.save(user);
    // }

    // public void deleteUser(Long id) {
    //     userRepository.deleteById(id);
    // }
}
