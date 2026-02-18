package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.services.ContestService;

public class CreateContestCommand implements ICommand {

    private final ContestService contestService; 

    public CreateContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {

        if (tokens.size() != 5) {
            throw new RuntimeException(
                "Invalid command. Usage: CreateContest <name> <level> <userId> <numQuestion>"
            );
        }

        String contestName = tokens.get(1);
        DifficultyLevel level = DifficultyLevel.valueOf(tokens.get(2));
        Long userId = Long.parseLong(tokens.get(3));
        Integer numQuestion = Integer.parseInt(tokens.get(4));

        Contest contest = contestService.createContest(
                contestName,
                level,
                userId,
                numQuestion
        );

        System.out.println("Contest Id: " + contest.getId());
    }
    
}
