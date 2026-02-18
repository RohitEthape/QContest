package com.crio.qcontest.commands;

import java.util.*;
import java.util.stream.Collectors;
import com.crio.qcontest.entities.Contest;
import com.crio.qcontest.entities.Contestant;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.services.ContestService;

public class ContestHistoryCommand implements ICommand {

    private final ContestService contestService;

    public ContestHistoryCommand(ContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void invoke(List<String> tokens) {


      if (tokens.size() != 2) {
        throw new RuntimeException(
            "Invalid command. Usage: ContestHistory <contestId>"
        );
    }

    Long contestId = Long.parseLong(tokens.get(1));

    List<Contestant> contestants =
            contestService.contestHistory(contestId);

    for (Contestant contestant : contestants) {

        String questionIds = contestant.getSolvedQuestions()
                .stream()
                .map(q -> String.valueOf(q.getId()))
                .collect(Collectors.joining(","));

        System.out.println(
            contestant.getUser().getName() +
            " : " +
            contestant.getTotalScore() +
            " [" + questionIds + "]"
        );
    }

    }
    
}
