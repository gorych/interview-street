package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "interviews")
public class Interview implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "hide")
    private boolean hide;

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

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hide=" + hide +
                ", placementDate=" + placementDate +
                ", type=" + type +
                '}';
    }
}
