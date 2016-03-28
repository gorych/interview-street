package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer_types")
public class AnswerType implements Serializable{

    @Id
    @Expose
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "type")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String type) {
        this.name = type;
    }

    @Override
    public String toString() {
        return "AnswerType{" +
                "id=" + id +
                ", type='" + name + '\'' +
                '}';
    }
}
