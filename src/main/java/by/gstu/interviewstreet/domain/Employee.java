package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "firstname", length = 10)
    private String firstname;

    @Column(name = "secondname", length = 20)
    private String secondname;

    @Column(name = "lastname", length = 25)
    private String lastname;

    @Expose
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "subdivision_id")
    private Subdivision subdivision;

    public Employee() {
    }

    public Employee(String firstname, String secondname, String lastname, Post post, Subdivision subdivision) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.lastname = lastname;
        this.post = post;
        this.subdivision = subdivision;
    }

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

    public String getFullName() {
        return WordUtils.capitalize((lastname + " " + firstname + " " + secondname).toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder()
                .append(getId(), employee.getId())
                .append(getFirstname(), employee.getFirstname())
                .append(getSecondname(), employee.getSecondname())
                .append(getLastname(), employee.getLastname())
                .append(getPost(), employee.getPost())
                .append(getSubdivision(), employee.getSubdivision())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getFirstname())
                .append(getSecondname())
                .append(getLastname())
                .append(getPost())
                .append(getSubdivision())
                .toHashCode();
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
