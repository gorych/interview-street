package by.gstu.interviewstreet.domain;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class ExtendUserInterview extends UserInterview {

    private Set<Post> posts;

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return super.toString() + "posts=" + posts + '}';
    }
}
