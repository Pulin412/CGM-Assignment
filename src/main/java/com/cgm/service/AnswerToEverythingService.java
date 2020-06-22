package com.cgm.service;

import com.cgm.dao.QuestionDAO;
import com.cgm.entity.Answer;
import com.cgm.entity.Question;
import com.cgm.util.AppUtil;
import com.cgm.util.Constants;
import org.h2.util.StringUtils;

import java.util.Set;
import java.util.logging.Logger;

public class AnswerToEverythingService {

    private static Logger log = Logger.getLogger(AnswerToEverythingService.class.getName());
    private QuestionDAO questionDAO;

    public AnswerToEverythingService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    /*
        Main method to process the input and return appropriate response.
     */
    public String evaluateInput(String[] input){

        log.info("ANSWER-TO-EVERYTHING :::::: received input");
        /*
            Checking if the input is not null or empty i.e. without any question.
         */
        if(input == null || input.length == 0){
            log.warning("ANSWER-TO-EVERYTHING :::::: Null or empty input.");
            return Constants.RESPONSE_NULL_INPUT;
        }

        int questionIndex = 0;
        boolean isValidQuestion = false;
        StringBuilder questionBuilder = new StringBuilder();

        /*
            Logic to extract the question from the input -
                - Loop through all the incoming input arguments.
                - Save all the strings into a StringBuilder to form the question.
                - As soon as '?' comes, break the loop and use the created string builder as question.
         */
        for (String word : input) {
            if(!StringUtils.isNullOrEmpty(word)){
                questionBuilder.append(word).append(" ");
                if(word.contains("?")){
                    isValidQuestion = true;
                    break;
                }
                questionIndex++;
            }
        }
        String question = questionBuilder.toString().trim();
        log.info("ANSWER-TO-EVERYTHING :::::: incoming question - " + question);
        /*
            validate the question for the length of the incoming question, should be less than 255 characters.
            If its not validated, throw an IllegalArgumentException with proper message string.
         */
        if(AppUtil.isQuestionValid(isValidQuestion, question)){
            /*
                check if only question is received or answers are also there in the input.
             */
            if(input.length == questionIndex + 1){
                /*
                    If only question is there, find the answers from the Database and print them in seperated lines.
                    If question is not present in the database, return with the generic answer.
                    If question is present in the database but there are no answers saved, return message to the user - "No answers saved.."
                 */
                Question answerObj = questionDAO.getAnswersFromDb(question);
                if(answerObj != null){
                    log.info("ANSWER-TO-EVERYTHING :::::: fetching answers for --  " + question);
                    Set<Answer> fromDbAnswers = answerObj.getAnswers();
                    if(fromDbAnswers != null && fromDbAnswers.size() > 0){
                        fromDbAnswers.forEach(answer -> System.out.println("- " + answer.getAnswer()));
                        return "";
                    }else{
                        return Constants.RESPONSE_NO_ANSWERS_IN_DB;
                    }
                }else{
                    return Constants.RESPONSE_DEFAULT_ANSWER;
                }
            }else{
                /*
                    If question is there with answers as well, save the question in the database.
                 */
                return addQuestion(input, questionIndex, question);
            }
        }else{
            throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_INVALID_QUESTION);
        }
    }

    private String addQuestion(String[] input, int questionIndex, String question) {
        log.info("ANSWER-TO-EVERYTHING :::::: saving --  " + question);
        return questionDAO.saveQuestion(AppUtil.questionMapper(input, questionIndex, question));
    }
}
