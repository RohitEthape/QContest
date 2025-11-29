package model;

import java.util.Objects;

public final class User {
    private final Long id;
    private final String name;
    private final int score; // global score (e.g., starts at 1500)

    public User(String name) {
        this.id = null;
        this.name = Objects.requireNonNull(name);
        this.score = 1500; // default as per slide 35
    }

    public User(Long id, String name, int score) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.score = score;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getScore() { return score; }

    public User withId(Long id) {
        return new User(id, this.name, this.score);
    }

    public User withScore(int newScore) {
        return new User(this.id, this.name, newScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(id, u.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }

    @Override
    public String toString() {
        return "User{" + id + ", name='" + name + "', score=" + score + "}";
    }
}
