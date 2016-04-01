package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "interview_types")
public class InterviewType implements Serializable {

    private static final int OPEN_TYPE_ID = 1;
    private static final String OPEN_TYPE_NAME = "open";

    private static final String VISIBILITY_ICON = "visibility";
    private static final String VISIBILITY_OFF_ICON = "visibility_off";

    private static final String VISIBILITY_ICON_TITLE = "Открытая анкета";
    private static final String VISIBILITY_OFF_ICON_TITLE = "Анонимная анкета";

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @NotEmpty
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

    public boolean isOpen() {
        return OPEN_TYPE_NAME.equals(name) || OPEN_TYPE_ID == id;
    }

    public String getVisibilityIcon() {
        return OPEN_TYPE_ID == id ? VISIBILITY_ICON : VISIBILITY_OFF_ICON;
    }

    public String getTitle() {
        return OPEN_TYPE_ID == id ? VISIBILITY_ICON_TITLE : VISIBILITY_OFF_ICON_TITLE;
    }

    @Override
    public String toString() {
        return "InterviewType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
