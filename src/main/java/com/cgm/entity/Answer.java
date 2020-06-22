package com.cgm.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name="answer_id")
    private Integer answerId;

    @Column(length = 255)
    private String answer;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "question_id", nullable = false)
//    private Question question;

    public Answer() {
    }

    public Answer(Integer answerId, String answer) {
        this.answerId = answerId;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer [id=" + answerId + ", answer=" + answer + "]";
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
