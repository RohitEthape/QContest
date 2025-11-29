package model;

import java.util.Objects;

public final class Question {
    private final Long id;
    private final String title;
    private final DifficultyLevel level;
    private final int score;

    // Constructor for new objects (id not set)
    public Question(String title, DifficultyLevel level, int score) {
        this.id = null;
        this.title = Objects.requireNonNull(title, "title");
        this.level = Objects.requireNonNull(level, "level");
        if (score <= 0) throw new IllegalArgumentException("score must be > 0");
        this.score = score;
    }

    // Constructor with id (used by repository)
    public Question(Long id, String title, DifficultyLevel level, int score) {
        this.id = Objects.requireNonNull(id, "id");
        this.title = Objects.requireNonNull(title, "title");
        this.level = Objects.requireNonNull(level, "level");
        this.score = score;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public DifficultyLevel getLevel() { return level; }
    public int getScore() { return score; }

    public Question withId(Long id) {
        return new Question(id, this.title, this.level, this.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question q = (Question) o;
        return Objects.equals(id, q.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "Question{" + id + ", title='" + title + "', level=" + level + ", score=" + score + "}";
    }
}
