package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question implements Serializable, Comparable<Question> {

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    QuestionType type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    Interview interview;

    @Expose
    @NotEmpty
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "number")
    private int number;

    @Expose
    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<UserAnswer> userAnswers = new ArrayList<>();

    public Question() {
    }

    public Question(Interview interview, QuestionType type, int number, String text) {
        this.interview = interview;
        this.number = number;
        this.type = type;
        this.text = text;
    }

    public List<Answer> getSortedAnswers() {
        Collections.sort(answers);
        return answers;
    }

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public boolean isRateType(){
        return getType().isRateType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return getId() == question.getId();

    }

    @Override
    public int compareTo(Question q) {
        return getNumber() - q.getNumber();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", number=" + number +
                ", type=" + type +
                ", interview=" + interview +
                '}';
    }

}