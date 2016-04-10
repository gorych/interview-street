package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.WordUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "secondname")
    private String secondname;

    @Column(name = "lastname")
    private String lastname;

    @Expose
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "subdivision_id")
    private Subdivision subdivision;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public String getInitials() {
        return WordUtils.capitalize((firstname + " " + secondname).toLowerCase());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", post=" + post +
                ", subdivision=" + subdivision +
                '}';
    }
}
