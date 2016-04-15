package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Column(name = "passportData")
    private String passportData;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Interview> interviews = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", employee=" + employee +
                ", role=" + role +
                ", passportData='" + passportData + '\'' +
                '}';
    }
}
