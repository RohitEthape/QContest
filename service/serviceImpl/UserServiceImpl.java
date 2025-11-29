package service.serviceImpl;

import model.User;
import repository.repositoryInterface.UserRepository;
import service.serviceInterface.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name empty");
        User u = new User(name);
        return repo.save(u);
    }

    @Override
    public List<app.User> leaderboard(boolean ascending) {
        Comparator<User> cmp = Comparator.comparingInt(User::getScore).thenComparing(User::getId);
        if (!ascending) cmp = cmp.reversed();
        return user.findAll().stream().sorted(cmp).collect(Collectors.toList());
    }

}
