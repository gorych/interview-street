package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "interviews")
public class Interview implements Serializable {

    private static final String LOCK_ICON = "lock";
    private static final String LOCK_OPEN_ICON = "lock_open";

    private static final String LOCK_ICON_TITLE = "Анкета закрыта для прохождения";
    private static final String LOCK_OPEN_ICON_TITLE = "Анкета открыта для прохождения";

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;

    @Size(min = 1, max = 100)
    @Column(name = "description")
    private String description;

    @Column(name = "hide")
    private boolean hide;

    @Column(name = "hash")
    private long hash;

    @Column(name = "placement_date")
    private Date placementDate;

   // private Date dateOff;

    @Column(name = "question_count")
    private long questionCount;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "type_id")
    private InterviewType type;

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

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

   /* public Date getDateOff() {
        return dateOff;
    }

    public void setDateOff(Date dateOff) {
        this.dateOff = dateOff;
    }*/

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

    public String getLockIcon() {
        return hide ? LOCK_ICON : LOCK_OPEN_ICON;
    }

    public String getTitle() {
        return hide ? LOCK_ICON_TITLE : LOCK_OPEN_ICON_TITLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interview interview = (Interview) o;

        if (getId() != interview.getId()) return false;
        if (isHide() != interview.isHide()) return false;
        if (getHash() != interview.getHash()) return false;
        if (getQuestionCount() != interview.getQuestionCount()) return false;
        if (!getName().equals(interview.getName())) return false;
        if (getDescription() != null ? !getDescription().equals(interview.getDescription()) : interview.getDescription() != null)
            return false;
        if (!getPlacementDate().equals(interview.getPlacementDate())) return false;
       // if (!getDateOff().equals(interview.getDateOff())) return false;
        return getType().equals(interview.getType());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (isHide() ? 1 : 0);
        result = 31 * result + (int) (getHash() ^ (getHash() >>> 32));
        result = 31 * result + getPlacementDate().hashCode();
      //  result = 31 * result + getDateOff().hashCode();
        result = 31 * result + (int) (getQuestionCount() ^ (getQuestionCount() >>> 32));
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hide=" + hide +
                ", hash=" + hash +
                ", placementDate=" + placementDate +
                ", dateOff=" + /*dateOff*/
                ", questionCount=" + questionCount +
                ", type=" + type +
                '}';
    }
}
