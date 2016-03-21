package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "interview_types")
public class InterviewType implements Serializable {

    private static final String OPEN_INTERVIEW_TYPE_NAME = "open";

    private static final String VISIBILITY_ICON = "visibility";
    private static final String VISIBILITY_OFF_ICON = "visibility_off";

    private static final String VISIBILITY_ICON_TITLE = "Открытая анкета";
    private static final String VISIBILITY_OFF_ICON_TITLE = "Анонимная анкета";

    @Id
    @Column(name = "id")
    @GeneratedValue
    @Expose

    private int id;

    @Column(name = "name")
    @NotNull
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

    public String getVisibilityIcon() {
        return name.equals(OPEN_INTERVIEW_TYPE_NAME) ? VISIBILITY_ICON : VISIBILITY_OFF_ICON;
    }

    public String getTitle() {
        return name.equals(OPEN_INTERVIEW_TYPE_NAME) ? VISIBILITY_ICON_TITLE : VISIBILITY_OFF_ICON_TITLE;
    }

    @Override
    public String toString() {
        return "InterviewType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
