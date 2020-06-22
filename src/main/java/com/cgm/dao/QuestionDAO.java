package com.cgm.dao;

import com.cgm.entity.Question;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/*
    DAO layer to interact with database using Hibernate ORM.
 */
public class QuestionDAO {

    /*
        Method to save any question.
            - It first checks if any question is already present in the database with the same question string.
            - If the question is already present, message conveying the same returns to the user otherwise the question gets added in the Database.
     */
    public String saveQuestion(Question question, Session session){
        Question questionFromDb = findQuestionByQuestionString(question.getQuestion(), session);
        if(questionFromDb != null)
            return "Question is already present.";
        else{
            session.save(question);
            session.getTransaction().commit();
            session.close();
            return "question added successfully";
        }
    }

    /*
        Method to find the question object from the Database to get the set of answers.
     */
    public Question getAnswersFromDb(String question, Session session){
        Question questionFromDb = findQuestionByQuestionString(question, session);
        session.getTransaction().commit();
        session.close();
        return questionFromDb;
    }

    /*
        Method to create query to look for question in the database on the basis of question string using CriteriaBuilder.
     */
    private static Question findQuestionByQuestionString(String questionString, Session session){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        Root<Question> root = query.from(Question.class);
        query.select(root).where(builder.equal(root.get("question"), questionString));
        Query<Question> q=session.createQuery(query);
        if(q.getResultList().size() > 0)
            return q.getSingleResult();
        else
            return null;
    }
}
