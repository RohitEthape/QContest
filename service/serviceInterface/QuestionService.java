package service.serviceInterface;

import model.DifficultyLevel;
import model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(String title, DifficultyLevel level, int score);
    List<Question> listQuestions(Optional<DifficultyLevel> level);

}
