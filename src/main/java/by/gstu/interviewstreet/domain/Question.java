package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questions")
public class Question implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "text")
    private String text;

    public Question() {
    }

    public Question(String text) {
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}