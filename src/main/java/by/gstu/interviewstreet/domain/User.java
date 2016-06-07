package by.gstu.interviewstreet.domain;

import com.google.gson.annotations.Expose;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Expose
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    /**
     * Not implemented in DB
     */
    @Transient
    private boolean active = true;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Interview> createdInterviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserInterview> interviewsForPassing = new ArrayList<>();

    public User() {
    }

    public User(Employee employee, UserRole role, String username, String password) {
        this.employee = employee;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Interview> getCreatedInterviews() {
        return createdInterviews;
    }

    public void setCreatedInterviews(List<Interview> interviews) {
        this.createdInterviews = interviews;
    }

    public List<UserInterview> getInterviewsForPassing() {
        return interviewsForPassing;
    }

    public void setInterviewsForPassing(List<UserInterview> interviewsForPassing) {
        this.interviewsForPassing = interviewsForPassing;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(getRole());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", employee=" + employee +
                ", role=" + role +
                ", username='" + username + '\'' +
                '}';
    }
}
