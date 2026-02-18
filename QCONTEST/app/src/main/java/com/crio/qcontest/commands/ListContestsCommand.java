package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.services.ContestService;

public class ListContestsCommand implements ICommand {

    private final ContestService contestService;

    public ListContestsCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {



      if (tokens.size() != 2) {
        throw new RuntimeException(
            "Invalid command. Usage: ListContests <level>"
        );
    }

    DifficultyLevel level =
            DifficultyLevel.valueOf(tokens.get(1));

    List<Contest> contests =
            contestService.listContests(level);

    System.out.println(contests.toString());    }
    
}
