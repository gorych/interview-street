package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer_types")
public class AnswerType implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnswerType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
