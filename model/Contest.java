package model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Contest {
    private final Long id;
    private final String title;
    private final DifficultyLevel level;
    private final Long createdByUserId;
    private final List<Question> questions; // immutable view

    public Contest(String title, DifficultyLevel level, Long createdByUserId, List<Question> questions) {
        this.id = null;
        this.title = Objects.requireNonNull(title);
        this.level = Objects.requireNonNull(level);
        this.createdByUserId = Objects.requireNonNull(createdByUserId);
        this.questions = Collections.unmodifiableList(Objects.requireNonNull(questions));
        if (questions.isEmpty()) throw new IllegalArgumentException("contest must have questions");
    }

    public Contest(Long id, String title, DifficultyLevel level, Long createdByUserId, List<Question> questions) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.level = Objects.requireNonNull(level);
        this.createdByUserId = Objects.requireNonNull(createdByUserId);
        this.questions = Collections.unmodifiableList(Objects.requireNonNull(questions));
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public DifficultyLevel getLevel() { return level; }
    public Long getCreatedByUserId() { return createdByUserId; }
    public List<Question> getQuestions() { return questions; }

    public Contest withId(Long id) {
        return new Contest(id, this.title, this.level, this.createdByUserId, this.questions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contest)) return false;
        Contest c = (Contest) o;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Contest{" + id + ", title='" + title + "', level=" + level + ", by=" + createdByUserId + ", questions=" + questions.size() + "}";
    }
}
