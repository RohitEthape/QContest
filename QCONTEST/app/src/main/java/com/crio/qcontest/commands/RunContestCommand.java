package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.services.ContestService;

public class RunContestCommand implements ICommand {

    private final ContestService contestService;

    public RunContestCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {

        if (tokens.size() != 3) {
            throw new RuntimeException(
                "Invalid command. Usage: RunContest <contestId> <creatorId>"
            );
        }

        Long contestId = Long.parseLong(tokens.get(1));
        Long creatorId = Long.parseLong(tokens.get(2));

        contestService.runContest(contestId, creatorId);
    }
    
}
