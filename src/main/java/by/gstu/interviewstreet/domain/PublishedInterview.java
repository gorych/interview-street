package by.gstu.interviewstreet.domain;

import by.gstu.interviewstreet.web.util.DateUtils;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "published_interviews")
public class PublishedInterview {

    @Id
    @Expose
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Expose
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "date")
    private Date publishDate;

    public PublishedInterview() {
    }

    public PublishedInterview(Interview interview) {
        this.interview = interview;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getFormatDate() {
        return DateUtils.YYYY_MM_DD_HH_MM_SS.format(publishDate);
    }

    @Override
    public String toString() {
        return "PublishedInterview{" +
                "id=" + id +
                ", interview=" + interview +
                ", publishDate=" + publishDate +
                '}';
    }
}
