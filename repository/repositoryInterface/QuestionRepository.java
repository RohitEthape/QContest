package repository.repositoryInterface;

import model.DifficultyLevel;
import model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question q);
    Optional<Question> findById(Long id);
    List<Question> findAll();
    List<Question> findByLevel(DifficultyLevel level);
}
