package by.gstu.interviewstreet.helpers;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Post;

import java.io.Serializable;
import java.util.Set;

public class UserInterviewHelper implements Serializable {

    Interview interview;
    Set<Post> posts;

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "UserInterviewHelper{" +
                "interview=" + interview +
                ", posts=" + posts +
                '}';
    }
}
