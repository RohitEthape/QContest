package repository.repositoryImpl;

import model.User;
import repository.repositoryInterface.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public User save(User u) {
        Long id = idCounter.incrementAndGet();
        User persisted = u.withId(id);
        storage.put(id, persisted);
        return persisted;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User update(User u) {
        if (u.getId() == null) throw new IllegalArgumentException("id required");
        storage.put(u.getId(), u);
        return u;
    }
}
