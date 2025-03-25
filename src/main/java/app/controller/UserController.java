package app.controller;

import app.model.User;
import app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // @Autowired
    // public UserController(UserService userService) {
    //     this.userService = userService;
    // }

    // @GetMapping("/{id}")
    // public User getUserById(@PathVariable Long id) {
    //     return userService.getUserById(id);
    // }

    // @PostMapping("/create/user")
    // public User createUser(@RequestBody User user) {
    //     return userService.createUser(user);
    // }

    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathVariable Long id) {
    //     userService.deleteUser(id);
    // }
}