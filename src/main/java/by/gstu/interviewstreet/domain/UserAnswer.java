package by.gstu.interviewstreet.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "user_answers")
public class UserAnswer implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Column(name = "answer")
    private String answer;

    @Column(name = "reply_date")
    private Date replyDate;

    public UserAnswer() {
    }

    public UserAnswer(User user, Question question, Interview interview, String answer, Date replayDate) {
        this.user = user;
        this.question = question;
        this.interview = interview;
        this.answer = answer;
        this.replyDate = replayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replayDate) {
        this.replyDate = replayDate;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", user=" + user +
                ", question=" + question +
                ", interview=" + interview +
                ", answer='" + answer + '\'' +
                ", replyDate=" + replyDate +
                '}';
    }
}
