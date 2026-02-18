package com.crio.qcontest.commands;

import java.util.List;

import com.crio.qcontest.entities.DifficultyLevel;
import com.crio.qcontest.entities.Question;
import com.crio.qcontest.services.QuestionService;

public class ListQuestionsCommand implements ICommand{

    private final QuestionService questionService;

    public ListQuestionsCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void invoke(List<String> tokens) {

      if (tokens.size() != 2) {
        throw new RuntimeException(
            "Invalid command. Usage: ListQuestions <level>"
        );
    }

    DifficultyLevel level =
            DifficultyLevel.valueOf(tokens.get(1));

    List<Question> questions =
            questionService.listQuestions(level);

    System.out.println(questions.toString());    }
    
}
