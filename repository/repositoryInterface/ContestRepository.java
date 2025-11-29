package repository.repositoryInterface;

import model.Contest;

import java.util.List;
import java.util.Optional;

public interface ContestRepository {
    Contest save(Contest c);
    Optional<Contest> findById(Long id);
    List<Contest> findAll();
}
