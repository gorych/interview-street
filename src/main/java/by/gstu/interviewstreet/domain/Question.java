package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @NotEmpty
    @Column(name = "text")
    private String text;

    @NotNull
    @Column(name = "number")
    private int number;

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
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public Question() { }

    public Question(Interview interview, QuestionType type, int number, String text) {
        this.interview = interview;
        this.number = number;
        this.type = type;
        this.text = text;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return getId() == question.getId();

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