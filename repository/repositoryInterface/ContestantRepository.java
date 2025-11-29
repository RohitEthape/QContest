package repository.repositoryInterface;

import model.Contestant;

import java.util.List;
import java.util.Optional;

public interface ContestantRepository {
    Contestant save(Contestant contestant);
    Optional<Contestant> findByUserIdAndContestId(Long userId, Long contestId);
    List<Contestant> findByContestId(Long contestId);
    void delete(Long id);
    Optional<Contestant> findById(Long id);
    Contestant update(Contestant c);
}
