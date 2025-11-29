package repository.repositoryInterface;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User u);
    Optional<User> findById(Long id);
    List<User> findAll();
    User update(User u); // return updated instance
}
