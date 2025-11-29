package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Contestant {
    private final Long id;
    private final Long userId;
    private final Long contestId;
    private int currentContestPoints; // mutable - aggregated during contest run
    private final List<Long> attemptedQuestionIds; // ids of solved questions

    // Creation constructor (id null)
    public Contestant(Long userId, Long contestId) {
        this.id = null;
        this.userId = Objects.requireNonNull(userId);
        this.contestId = Objects.requireNonNull(contestId);
        this.currentContestPoints = 0;
        this.attemptedQuestionIds = new ArrayList<>();
    }

    // With id (persisted)
    public Contestant(Long id, Long userId, Long contestId, int currentContestPoints, List<Long> attemptedQuestionIds) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.contestId = Objects.requireNonNull(contestId);
        this.currentContestPoints = currentContestPoints;
        this.attemptedQuestionIds = new ArrayList<>(Objects.requireNonNull(attemptedQuestionIds));
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getContestId() { return contestId; }
    public int getCurrentContestPoints() { return currentContestPoints; }
    public List<Long> getAttemptedQuestionIds() { return Collections.unmodifiableList(attemptedQuestionIds); }

    // mutate contestant when solves a question
    public void solveQuestion(Long questionId, int questionScore) {
        if (!attemptedQuestionIds.contains(questionId)) {
            attemptedQuestionIds.add(questionId);
            currentContestPoints += questionScore;
        }
    }

    public Contestant withId(Long id) {
        return new Contestant(id, this.userId, this.contestId, this.currentContestPoints, this.attemptedQuestionIds);
    }

    @Override
    public String toString() {
        return "Contestant{" + id + ", user=" + userId + ", contest=" + contestId + ", pts=" + currentContestPoints + "}";
    }
}
