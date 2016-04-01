package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import javafx.geometry.Pos;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "posts")
public class Post implements Serializable {

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @NotEmpty
    @Column(name = "name")
    private String name;

    public Post(){ }

    public Post(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
