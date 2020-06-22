package com.cgm;

import com.cgm.dao.QuestionDAO;
import com.cgm.service.AnswerToEverythingService;
import com.cgm.service.IAnswerToEverythingService;
import com.cgm.util.HibernateUtil;

/*
    Starting point of the application.
 */
public class AnswerToEverythingApplication {
    public static void main(String[] args) {

        /*
            Coupling DAO object with the service object. Spring dependency injection can also be used if the solutions grows to be more complex with multiple classes.
         */
        IAnswerToEverythingService service = new AnswerToEverythingService(new QuestionDAO(HibernateUtil.getSessionFactory()));
        System.out.println(service.evaluateInput(args));
    }
}