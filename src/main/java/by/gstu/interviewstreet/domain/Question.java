package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question implements Serializable {

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "text")
    private String text;

    @Expose
    @Column(name = "number")
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="interview_id")
    Interview interview;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();

    public Question() { }

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

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", number=" + number +
                ", interview=" + interview +
                ", answers=" + answers +
                '}';
    }
}