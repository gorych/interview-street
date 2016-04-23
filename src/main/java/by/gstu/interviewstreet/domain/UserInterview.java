package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

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

    @Override
    public String toString() {
        return "UserInterview{" +
                "id=" + id +
                ", interview=" + interview +
                ", user=" + user +
                '}';
    }
}
