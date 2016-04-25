package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "question_types")
public class QuestionType implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Expose
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Expose
    @NotEmpty
    @Column(name = "icon")
    private String icon;

    @Expose
    @NotEmpty
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "default_answer_count")
    private int answerCount;

    @NotNull
    @Column(name = "min_answer_count")
    private int minAnswerCount;

    @NotNull
    @OneToOne(mappedBy = "questionType")
    private AnswerType answerType;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getMinAnswerCount() {
        return minAnswerCount;
    }

    public void setMinAnswerCount(int minAnswerCount) {
        this.minAnswerCount = minAnswerCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public boolean isRateType() {
        final String ratingQuestionName = "rating";
        return ratingQuestionName.equals(name);
    }

    @Override
    public String toString() {
        return "QuestionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", answerCount=" + answerCount +
                ", minAnswerCount=" + minAnswerCount +
                '}';
    }
}
