package by.gstu.interviewstreet.domain;

import by.gstu.interviewstreet.util.DateUtils;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_interviews")
public class UserInterview implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Expose
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "isPassed")
    private boolean isPassed;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "passing_date")
    private Date passingDate;

    public UserInterview(){  }

    public UserInterview(Interview interview, User user) {
        this.interview = interview;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getPassed() {
        return isPassed;
    }

    public void setPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public Date getPassingDate() {
        return passingDate;
    }

    public String getFormatPassingDate() {
        return DateUtils.YYYY_MM_DD_HH_MM_SS.format(passingDate);
    }

    public void setPassingDate(Date passingDate) {
        this.passingDate = passingDate;
    }

    @Override
    public String toString() {
        return "UserInterview{" +
                "id=" + id +
                ", interview=" + interview +
                ", user=" + user +
                ", isPassed=" + isPassed +
                ", passingDate=" + passingDate +
                '}';
    }
}
