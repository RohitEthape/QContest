package service.serviceInterface;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(String name);
    List<app.User> leaderboard(boolean ascending);

    Optional<User> findById(Long userId);
}
