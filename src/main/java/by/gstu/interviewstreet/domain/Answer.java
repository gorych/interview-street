package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {

    @Id
    @Expose
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Expose
    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AnswerType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnswerType getType() {
        return type;
    }

    public void setType(AnswerType type) {
        this.type = type;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", type=" + type +
                ", question=" + question +
                '}';
    }
}
