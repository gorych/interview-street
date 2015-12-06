package by.gstu.interviewstreet.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "interviews")
public class Interview implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
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

    public Date getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(Date placementDate) {
        this.placementDate = placementDate;
    }

    public InterviewType getType() {
        return type;
    }

    public void setType(InterviewType type) {
        this.type = type;
    }

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
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
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interview)) return false;

        Interview interview = (Interview) o;

        if (getId() != interview.getId()) return false;
        if (isHide() != interview.isHide()) return false;
        if (!getName().equals(interview.getName())) return false;
        if (!getDescription().equals(interview.getDescription())) return false;
        if (!getPlacementDate().equals(interview.getPlacementDate())) return false;
        return getType().equals(interview.getType());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + (isHide() ? 1 : 0);
        result = 31 * result + getPlacementDate().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}
