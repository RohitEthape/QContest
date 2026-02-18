package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.services.QuestionService;

public class CreateQuestionCommand implements ICommand{
    private final QuestionService questionService;

    public CreateQuestionCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void invoke(List<String> tokens) {

        if (tokens.size() != 4) {
            throw new RuntimeException(
                "Invalid command. Usage: CreateQuestion <name> <level> <score>"
            );
        }

        String name = tokens.get(1);
        DifficultyLevel level = DifficultyLevel.valueOf(tokens.get(2));
        Integer score = Integer.parseInt(tokens.get(3));

        Question question = questionService.createQuestion(name, level, score);

        System.out.println("Question Id: " + question.getId());
    }

}
