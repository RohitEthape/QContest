package com.crio.qcontest.entities;

import java.util.List;

public class Contest {
    private final Long id;
    private final String name;
    private final DifficultyLevel level;
    private final User creator;
    private final List<Question> questions;

    // Constructor without id (for creation)
    public Contest(String name, DifficultyLevel level, User creator, List<Question> questions) {
        this.id = null;
        this.name = name;
        this.level = level;
        this.creator = creator;
        this.questions = questions;
    }

    // Constructor with id (used after saving)
    public Contest(Long id, Contest other) {
        this.id = id;
        this.name = other.name;
        this.level = other.level;
        this.creator = other.creator;
        this.questions = other.questions;
    }

    // Builder-based constructor
    private Contest(ContestBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.level = builder.level;
        this.creator = builder.creator;
        this.questions = builder.questions;
    }

    

    // Getters
    public String getName() { return name; }
    public DifficultyLevel getLevel() { return level; }
    public User getCreator() { return creator; }
    public List<Question> getQuestions() { return questions; }
    public Long getId() { return id; }

    @Override
    public String toString() {
        return "Contest [id=" + id + "]";
    }

    // ---------------------------
    //      BUILDER CLASS
    // ---------------------------
    public static class ContestBuilder {

        private Long id;
        private String name;
        private DifficultyLevel level;
        private User creator;
        private List<Question> questions;

        public ContestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ContestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ContestBuilder level(DifficultyLevel level) {
            this.level = level;
            return this;
        }

        public ContestBuilder creator(User creator) {
            this.creator = creator;
            return this;
        }

        public ContestBuilder questions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public Contest build() {
            return new Contest(this);
        }
    }
}
