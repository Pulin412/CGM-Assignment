package answerToEverything.service;

import answerToEverything.utils.TestHibernateUtil;
import answerToEverything.utils.TestUtils;
import com.cgm.dao.QuestionDAO;
import com.cgm.entity.Question;
import com.cgm.service.AnswerToEverythingService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionnaireServiceImplTest {

    @Mock
    private static SessionFactory sessionFactory;

    @Mock
    private static Session session;

    @InjectMocks
    private AnswerToEverythingService questionnaireService;

    @Mock
    private QuestionDAO questionDAO;

    @BeforeAll
    public void before() {
        MockitoAnnotations.initMocks(this);
        session = TestHibernateUtil.getSessionFactory().openSession();
        Mockito.when(sessionFactory.openSession()).thenReturn(session);
    }

    /*
        Positive scenario test cases -
            1. If valid question with answers is passed as an argument.
            2. If any new question is asked which is not stored in the system already.
            3. If any old question is asked which is already stored in the system.
     */
    @Test
    void test_when_validQuestionWithAnswers_then_QuestionAdded(){
        Mockito.when(questionDAO.saveQuestion(Mockito.any(Question.class))).thenReturn("question added successfully");
        Assertions.assertEquals("question added successfully",questionnaireService.evaluateInput(TestUtils.generate_ValidQuestionWithAnswers_Input()));
    }

    @Test
    void test_when_AskNewQuestion_then_PredefinedAnswerShouldBePrinted(){
        Mockito.when(questionDAO.getAnswersFromDb(Mockito.any(String.class))).thenReturn(null);
        Assertions.assertEquals("the answer to life, universe and everything is 42",questionnaireService.evaluateInput(TestUtils.generate_AskQuestion_Input()));
    }

    @Test
    void test_when_AskOldQuestion_then_SavedAnswerShouldBePrinted(){
        Mockito.when(questionDAO.getAnswersFromDb(Mockito.any(String.class))).thenReturn(TestUtils.getSavedQuestion());
        Assertions.assertEquals("",questionnaireService.evaluateInput(TestUtils.generate_AskQuestion_Input()));
    }

    /*
        Negative scenario test cases -
            1. If null is passed as an argument.
            2. If any question with more than 255 character is passed as an argument.
            3. If any answer with more than 255 character is passed as an argument.
            4. If any question with incorrect format (without '?') is passed as an argument.
     */
    @Test
    void test_when_NoArgumentsPassed_then_ErrorMessageShouldGetPrinted(){
        Assertions.assertEquals("Please enter question..",questionnaireService.evaluateInput(null));
    }

    @Test
    void test_when_QuestionLengthMoreThan255_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_InvalidQuestion_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Length of Question exceeds 255 characters"));
    }

    @Test
    void test_when_AnswerLengthMoreThan255_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_InvalidAnswer_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Length of Answer exceeds 255 characters"));
    }

    @Test
    void test_when_QuestionFormatIsWrong_then_ThrowException(){
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            questionnaireService.evaluateInput(TestUtils.generate_WrongQuestionFormat_Input());
        });
        Assertions.assertTrue(exception.getMessage().contains("Question format is incorrect"));
    }

    @Test
    void test_when_QuestionIsPresentWithoutAnyAnswerInDB_then_ThrowException(){
        Mockito.when(questionDAO.getAnswersFromDb(Mockito.any(String.class))).thenReturn(TestUtils.getSavedQuestionWithoutAnswer());
        Assertions.assertEquals("No answers saved..",questionnaireService.evaluateInput(TestUtils.generate_AskQuestion_Input()));
    }
}
