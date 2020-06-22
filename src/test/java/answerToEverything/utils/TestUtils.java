package answerToEverything.utils;

import com.cgm.entity.Answer;
import com.cgm.entity.Question;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static String[] generate_InvalidQuestion_Input(){
        return new String[] {"LoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoque?"};
    }

    public static String[] generate_InvalidAnswer_Input(){
        return new String[] {"Question?", "answer1", "LoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoqueLoremipsumdolorsitametconsectetueradipiscingelitAeneancommodoligulaegetdolorAeneanassaCumsociisnatoque"};
    }

    public static String[] generate_WrongQuestionFormat_Input(){
        return new String[] {"dummy", "answer1", "answer2"};
    }

    public static String[] generate_AskQuestion_Input(){
        return new String[] {"What is Peters favorite food?"};
    }

    public static String[] generate_ValidQuestionWithAnswers_Input(){
        return new String[] {"What is Peters favorite food?", "Pizza" ,"Spaghetti", "Ice cream"};
    }

    public static Question getSavedQuestion(){
        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestion("What is Peters favorite food?");
        Set<Answer> answerSet = new HashSet<>();
        answerSet.add(new Answer(100,"Pizza"));
        answerSet.add(new Answer(100,"Ice cream"));
        answerSet.add(new Answer(100,"Spaghetti"));
        question.setAnswers(answerSet);
        return question;
    }
}
