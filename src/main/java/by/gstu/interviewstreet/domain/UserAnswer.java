package by.gstu.interviewstreet.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_answers")
public class UserAnswer implements Serializable, Comparable<UserAnswer> {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    /*It's necessary for text answers, which user inputs himself*/
    @NotEmpty
    @Column(name = "answer")
    private String answerText;

    @Column(name = "reply_date")
    private Date replyDate;

    public UserAnswer() {
    }

    public UserAnswer(User user, Question question, Interview interview, Answer answer, String answerText, Date replayDate) {
        this.user = user;
        this.question = question;
        this.interview = interview;
        this.answer = answer;
        this.answerText = answerText;
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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
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
                ", answer=" + answer +
                ", answerText='" + answerText + '\'' +
                ", replyDate=" + replyDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAnswer that = (UserAnswer) o;

        return getAnswerText() != null ? getAnswerText().equals(that.getAnswerText()) : that.getAnswerText() == null;

    }

    @Override
    public int hashCode() {
        return getAnswerText() != null ? getAnswerText().hashCode() : 0;
    }

    @Override
    public int compareTo(UserAnswer ua) {
        return this.getAnswerText().compareTo(ua.getAnswerText());
    }
}
