package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "interview_types")
public class InterviewType implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "name")
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

    public void setType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InterviewType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
