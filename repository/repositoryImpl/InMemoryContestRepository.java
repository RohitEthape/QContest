package repository.repositoryImpl;

import model.Contest;
import repository.repositoryInterface.ContestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryContestRepository implements ContestRepository {
    private final Map<Long, Contest> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Contest save(Contest c) {
        Long id = idCounter.incrementAndGet();
        Contest persisted = c.withId(id);
        storage.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<Contest> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Contest> findAll() {
        return new ArrayList<>(storage.values());
    }
}
