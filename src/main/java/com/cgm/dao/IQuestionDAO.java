package com.cgm.dao;

import com.cgm.entity.Question;

public interface IQuestionDAO {
    public String saveQuestion(Question question);
    public Question getAnswersFromDb(String question);
}
