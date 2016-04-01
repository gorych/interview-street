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

    @Expose
    @Column(name = "type")
    private String name;

    @Expose
    @Column(name = "icon")
    private String icon;

    @Expose
    @Column(name = "title")
    private String title;

    @Expose
    @Column(name = "default_value")
    private String defaultValue;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "AnswerType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}
