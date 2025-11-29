package service.serviceImpl;

import model.*;
import repository.repositoryImpl.InMemoryContestantRepository;
import repository.repositoryInterface.ContestRepository;
import repository.repositoryInterface.ContestantRepository;
import repository.repositoryInterface.QuestionRepository;
import repository.repositoryInterface.UserRepository;
import service.serviceInterface.ContestService;

import java.util.*;
import java.util.stream.Collectors;

public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepo;
    private final QuestionRepository questionRepo;
    private final UserRepository userRepo;
    private final ContestantRepository contestantRepo;

    public ContestServiceImpl(ContestRepository contestRepo,
                              QuestionRepository questionRepo,
                              UserRepository userRepo,
                              InMemoryContestantRepository contestantRepo2) {
        this.contestRepo = contestRepo;
        this.questionRepo = questionRepo;
        this.userRepo = userRepo;
        this.contestantRepo = (ContestantRepository) contestantRepo2;
    }

    @Override
    public Contest createContest(String title, DifficultyLevel level, Long creatorUserId, int numQuestions) {
        // validation
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title empty");
        if (numQuestions <= 0) throw new IllegalArgumentException("numQuestions must be > 0");
        User creator = userRepo.findById(creatorUserId).orElseThrow(() -> new IllegalArgumentException("creator not found"));

        // pick questions of same level
        List<Question> available = questionRepo.findByLevel(level);
        if (available.size() < numQuestions) throw new IllegalArgumentException("not enough questions");

        // deterministic selection: first N
        List<Question> selected = new ArrayList<>(available.subList(0, numQuestions));

        Contest c = new Contest(title, level, creatorUserId, selected);
        return contestRepo.save(c);
    }

    @Override
    public List<Contest> listContests(Optional<DifficultyLevel> level) {
        List<Contest> all = contestRepo.findAll();
        if (level.isPresent()) {
            return all.stream().filter(c -> c.getLevel() == level.get()).collect(Collectors.toList());
        } else {
            return all;
        }
    }

    @Override
    public Contestant attendContest(Long contestId, Long userId) {
        Contest contest = contestRepo.findById(contestId).orElseThrow(() -> new IllegalArgumentException("contest not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found"));
        Optional<Contestant> existing = contestantRepo.findByUserIdAndContestId(userId, contestId);
        if (existing.isPresent()) throw new IllegalArgumentException("already attended");

        Contestant contestant = new Contestant(userId, contestId);
        return contestantRepo.save(contestant);
    }

    @Override
    public String withdrawContest(Long contestId, Long userId) {
        Optional<Contestant> c = contestantRepo.findByUserIdAndContestId(userId, contestId);
        if (c.isEmpty()) return "Contestant not found!";
        contestantRepo.delete(c.get().getId());
        return "Withdrawn successfully";
    }

    @Override
    public String runContest(Long contestId, Long requesterUserId) {
        Contest contest = contestRepo.findById(contestId).orElseThrow(() -> new IllegalArgumentException("contest not found"));

        if (!contest.getCreatedByUserId().equals(requesterUserId)) {
            return "Only the contest creator can run the contest!";
        }

        List<Contestant> contestants = contestantRepo.findByContestId(contestId);

        // For each contestant, compute points (we'll assume attemptedQuestionIds were recorded earlier).
        // In this simple model, contestants may have no attempts; update user global scores per slide 40 rules.
        for (Contestant contestant : contestants) {
            int pts = contestant.getCurrentContestPoints();
            // update user
            User user = userRepo.findById(contestant.getUserId()).orElseThrow();
            int newScore;
            switch (contest.getLevel()) {
                case LOW:
                    newScore = user.getScore() + (pts - 50);
                    break;
                case MEDIUM:
                    newScore = user.getScore() + (pts - 30);
                    break;
                case HIGH:
                default:
                    newScore = user.getScore() + pts;
                    break;
            }
            User updated = user.withScore(newScore);
            userRepo.update(updated);
            // persist contestant (we mutate the contestant instance in place then call update for repo)
            contestantRepo.update(contestant);
        }

        return "Contest run successfully";
    }

    @Override
    public List<Contestant> contestHistory(Long contestId) {
        List<Contestant> contestants = contestantRepo.findByContestId(contestId);
        contestants.sort(Comparator.comparingInt(Contestant::getCurrentContestPoints).reversed());
        return contestants;
    }
}
