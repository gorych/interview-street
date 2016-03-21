package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "interviews")
public class Interview implements Serializable {

    private static final String OPEN_TYPE = "open";

    private static final String LOCK_ICON = "lock";
    private static final String LOCK_OPEN_ICON = "lock_open";

    private static final String LOCK_ICON_TITLE = "Анкета закрыта для прохождения";
    private static final String LOCK_OPEN_ICON_TITLE = "Анкета открыта для прохождения";

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @Column(name = "description")
    private String description;

    @Expose
    @Column(name = "goal")
    private String goal;

    @Expose
    @Column(name = "audience")
    private String audience;

    @Expose
    @Column(name = "hide")
    private boolean isHide;

    @Expose
    @Temporal(TemporalType.DATE)
    @Generated(GenerationTime.INSERT)
    @Column(name = "placement_date")
    private Date placementDate;

    @Expose
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Expose
    @Column(name = "question_count")
    private long questionCount;

    @Expose
    @ManyToOne()
    @JoinColumn(name = "type_id")
    private InterviewType type;

    public boolean isOpen() {
        return OPEN_TYPE.equals(type.getName());
    }

    public String getLockIcon() {
        return isHide ? LOCK_ICON : LOCK_OPEN_ICON;
    }

    public String getTitle() {
        return isHide ? LOCK_ICON_TITLE : LOCK_OPEN_ICON_TITLE;
    }

    //region Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public boolean getHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        this.isHide = hide;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(long questionCount) {
        this.questionCount = questionCount;
    }

    public InterviewType getType() {
        return type;
    }

    public void setType(InterviewType type) {
        this.type = type;
    }
    //endregion

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", goal='" + goal + '\'' +
                ", audience='" + audience + '\'' +
                ", hide=" + isHide +
                ", placementDate=" + placementDate +
                ", endDate=" + endDate +
                ", questionCount=" + questionCount +
                ", type=" + type +
                '}';
    }

}
