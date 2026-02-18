package com.crio.qcontest.services;

 import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.qcontest.entities.User;
import com.crio.qcontest.repositories.IUserRepository;

public class UserService{

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user with the specified name.
     * @param name Name of the user.
     * @return Created User object.
     */
    public User createUser(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name empty");
        User u = new User(name);
        return userRepository.save(u);
    }

    /**
     * Retrieves a list of users sorted by their score.
     * @param order Sorting order ("ASC" for ascending, "DESC" for descending).
     * @return List of users sorted by score as per the specified order.
     */
    public List<User> showLeaderBoard(String order) {

        Comparator<User> cmp = Comparator
                .comparingInt(User::getScore)        // primary: score
                .thenComparing(User::getId);         // secondary: id
    
        if ("DESC".equalsIgnoreCase(order)) {
            cmp = cmp.reversed();   // High → Low
        }
    
        // For ASC order, do NOT reverse
    
        return userRepository.findAll()
                .stream()
                .sorted(cmp)
                .collect(Collectors.toList());
    }
    

    

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    } 
}