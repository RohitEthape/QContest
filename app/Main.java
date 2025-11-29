package app;

// import model.*;
// import repository.*;

// import repository.repositoryImpl.*;
// import service.*;
// import service.serviceImpl.*;
// import service.serviceInterface.*;

import java.util.List;
import java.util.Optional;

import model.Contest;
import model.Contestant;
import model.DifficultyLevel;
import model.Question;
import repository.repositoryImpl.InMemoryContestRepository;
import repository.repositoryImpl.InMemoryContestantRepository;
import repository.repositoryImpl.InMemoryQuestionRepository;
import repository.repositoryImpl.InMemoryUserRepository;
import service.serviceImpl.ContestServiceImpl;
import service.serviceImpl.QuestionServiceImpl;
import service.serviceImpl.UserServiceImpl;
import service.serviceInterface.ContestService;
import service.serviceInterface.QuestionService;
import service.serviceInterface.UserService;

public class Main {
    public static void main(String[] args) {

        // ----------------------------
        // Setup: Create repositories
        // ----------------------------
        InMemoryQuestionRepository questionRepo = new InMemoryQuestionRepository();
        InMemoryUserRepository userRepo = new InMemoryUserRepository();
        InMemoryContestRepository contestRepo = new InMemoryContestRepository();
        InMemoryContestantRepository contestantRepo = new InMemoryContestantRepository();

        // ----------------------------
        // Setup: Create services
        // ----------------------------
        QuestionService questionService = new QuestionServiceImpl(questionRepo);
        UserService userService = new UserServiceImpl(userRepo);
        ContestService contestService = new ContestServiceImpl(
                contestRepo, questionRepo, userRepo, contestantRepo
        );

        System.out.println("\n===== QCONTEST AUTOMATED EXECUTION =====");

        // ----------------------------
        // STEP 1: Create Users
        // ----------------------------
        model.User u1 = userService.createUser("Ross");
        model.User u2 = userService.createUser("Monica");
        model.User u3 = userService.createUser("Joey");

        System.out.println("\nUsers created:");
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);

        // ----------------------------
        // STEP 2: Create Questions
        // ----------------------------
        Question q1 = questionService.createQuestion("Two Sum", DifficultyLevel.LOW, 40);
        Question q2 = questionService.createQuestion("Reverse String", DifficultyLevel.LOW, 30);
        Question q3 = questionService.createQuestion("Palindrome", DifficultyLevel.LOW, 50);

        System.out.println("\nQuestions created:");
        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);

        // ----------------------------
        // STEP 3: Create Contest
        // ----------------------------
        Contest contest = contestService.createContest(
                "Warmup Contest", DifficultyLevel.LOW, u1.getId(), 3
        );

        System.out.println("\nContest created:");
        System.out.println(contest);

        // ----------------------------
        // STEP 4: Users Attend Contest
        // ----------------------------
        Contestant c1 = contestService.attendContest(contest.getId(), u1.getId());
        Contestant c2 = contestService.attendContest(contest.getId(), u2.getId());
        Contestant c3 = contestService.attendContest(contest.getId(), u3.getId());

        System.out.println("\nUsers attending contest:");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        // ----------------------------
        // STEP 5: Simulate Solving Questions
        // ----------------------------
        // (Normally you'd have a SOLVE_QUESTION API, here we directly update contestants)

        System.out.println("\nSimulating solved questions...");

        // Monica solves 2 questions
        c2.solveQuestion(q1.getId(), q1.getScore());
        c2.solveQuestion(q2.getId(), q2.getScore());
        contestantRepo.update(c2);

        // Joey solves all 3
        c3.solveQuestion(q1.getId(), q1.getScore());
        c3.solveQuestion(q2.getId(), q2.getScore());
        c3.solveQuestion(q3.getId(), q3.getScore());
        contestantRepo.update(c3);

        // Ross solves 1
        c1.solveQuestion(q3.getId(), q3.getScore());
        contestantRepo.update(c1);

        System.out.println("Ross points: " + c1.getCurrentContestPoints());
        System.out.println("Monica points: " + c2.getCurrentContestPoints());
        System.out.println("Joey points: " + c3.getCurrentContestPoints());

        // ----------------------------
        // STEP 6: Run Contest
        // ----------------------------
        System.out.println("\nRunning contest...");
        String result = contestService.runContest(contest.getId(), u1.getId());
        System.out.println("Run Contest Result: " + result);

        // ----------------------------
        // STEP 7: Leaderboard (DESC)
        // ----------------------------
        System.out.println("\n===== Leaderboard (DESC) =====");
        List<User> leaderboard = userService.leaderboard(false);
        leaderboard.forEach(u ->
                System.out.println(u.getName() + " : " + u.getScore())
        );

        // ----------------------------
        // STEP 8: Contest History
        // ----------------------------
        System.out.println("\n===== Contest History =====");
        List<Contestant> history = contestService.contestHistory(contest.getId());
        for (Contestant ct : history) {
            model.User user = userService.findById(ct.getUserId()).orElseThrow();
            System.out.println(
                    user.getName() + " : " +
                    ct.getCurrentContestPoints() + " " +
                    ct.getAttemptedQuestionIds()
            );
        }

        System.out.println("\n===== END OF EXECUTION =====");
    }
}
