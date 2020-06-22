package com.cgm.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    @Column(length = 255)
    private String question;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Answer> answers;

    public Question() {
    }

    public Question(Integer questionId, String question, Set<Answer> answers) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question [id=" + questionId + ", question=" + question + ", answers=" + answers + "]";
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
