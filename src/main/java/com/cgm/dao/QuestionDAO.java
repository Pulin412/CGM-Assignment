package com.cgm.dao;

import com.cgm.entity.Question;
import com.cgm.util.Constants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/*
    DAO layer to interact with database using Hibernate ORM.
 */
public class QuestionDAO implements IQuestionDAO{

    SessionFactory sf;

    public QuestionDAO(SessionFactory sf) {
        this.sf = sf;
    }

    private Session beginTransaction() {
        Session session = sf.openSession();
        session.beginTransaction();
        return session;
    }

    private void completeTransaction(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    /*
            Method to save any question.
                - It first checks if any question is already present in the database with the same question string.
                - If the question is already present, message conveying the same returns to the user otherwise the question gets added in the Database.
         */
    @Override
    public String saveQuestion(Question question) {

        Session session = beginTransaction();
        Question questionFromDb = findQuestionByQuestionString(question.getQuestion(), session);
        if (questionFromDb != null)
            return Constants.RESPONSE_QUESTION_ALREADY_PRESENT;
        else {
            session.save(question);
            completeTransaction(session);
            return Constants.RESPONSE_QUESTION_SAVE_MESSAGE;
        }
    }

    /*
        Method to find the question object from the Database to get the set of answers.
     */
    @Override
    public Question getAnswersFromDb(String question) {
        Session session = beginTransaction();
        Question questionFromDb = findQuestionByQuestionString(question, session);
        completeTransaction(session);
        return questionFromDb;
    }

    /*
        Method to create query to look for question in the database on the basis of question string using CriteriaBuilder.
     */
    private static Question findQuestionByQuestionString(String questionString, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        Root<Question> root = query.from(Question.class);
        query.select(root).where(builder.equal(root.get("question"), questionString));
        Query<Question> q = session.createQuery(query);
        if (q.getResultList().size() > 0)
            return q.getSingleResult();
        else
            return null;
    }
}
