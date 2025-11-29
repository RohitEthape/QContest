package repository.repositoryImpl;

import model.DifficultyLevel;
import model.Question;
import repository.repositoryInterface.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryQuestionRepository implements QuestionRepository {
    private final Map<Long, Question> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Question save(Question q) {
        Long id = idCounter.incrementAndGet();
        Question persisted = q.withId(id);
        storage.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Question> findByLevel(DifficultyLevel level) {
        return storage.values().stream().filter(q -> q.getLevel() == level).collect(Collectors.toList());
    }
}
