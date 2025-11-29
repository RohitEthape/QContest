package service.serviceInterface;

import model.Contest;
import model.Contestant;
import model.DifficultyLevel;

import java.util.List;
import java.util.Optional;

public interface ContestService {
    Contest createContest(String title, DifficultyLevel level, Long creatorUserId, int numQuestions);
    List<Contest> listContests(Optional<DifficultyLevel> level);
    Contestant attendContest(Long contestId, Long userId);
    String withdrawContest(Long contestId, Long userId);
    String runContest(Long contestId, Long requesterUserId);
    List<Contestant> contestHistory(Long contestId);
}
