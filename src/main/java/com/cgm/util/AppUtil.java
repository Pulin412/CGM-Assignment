package com.cgm.util;

import com.cgm.entity.Answer;
import com.cgm.entity.Question;

import java.util.HashSet;
import java.util.Set;

public class AppUtil {

    private static boolean validateInput(String input) {
        return input.length() >= Constants.QUESTION_MAX_CHARACTERS;
    }

    /*
        Check if the question is valid or not.
     */
    public static boolean isQuestionValid(boolean isValidQuestion, String question) {
        if(!isValidQuestion){
            throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_INCORRECT_FORMAT);
        }
        if(validateInput(question)){
            throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_QUESTION_CHARACTERS_EXCEED);
        }
        return true;
    }

    /*
        Validate the answers coming in the input.
        If any answer is more than 255 characters, thrown and exception.
        If all answers are valid, create objects for answer and question and return the question object to save in the database.
     */
    public static Question questionMapper(String[] input, int questionIndex, String question){
        Set<Answer> answerSet = new HashSet<>();
        for (int i = questionIndex + 1; i < input.length; i++) {
            String answerStr = input[i];
            if(AppUtil.validateInput(answerStr)){
                throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_ANSWERS_CHARACTERS_EXCEED);
            }
            Answer answer = new Answer();
            answer.setAnswer(answerStr);
            answerSet.add(answer);
        }
        Question persistQuestion = new Question();
        persistQuestion.setQuestion(question);
        persistQuestion.setAnswers(answerSet);
        return  persistQuestion;
    }
}
