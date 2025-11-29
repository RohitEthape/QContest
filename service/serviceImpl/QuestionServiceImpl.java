package service.serviceImpl;

import model.DifficultyLevel;
import model.Question;
import repository.repositoryInterface.QuestionRepository;
import service.serviceInterface.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repo;

    public QuestionServiceImpl(QuestionRepository repo) {
        this.repo = repo;
    }

    @Override
    public Question createQuestion(String title, DifficultyLevel level, int score) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title empty");
        Question q = new Question(title, level, score);
        return repo.save(q);
    }

    @Override
    public List<Question> listQuestions(Optional<DifficultyLevel> level) {
        if (level.isPresent()) {
            return repo.findByLevel(level.get());
        } else {
            return repo.findAll();
        }
    }

}
