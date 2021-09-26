package org.allisonkosy.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment implements Serializable, Model {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getBody() {
        return body;
    }

    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", user=" + user.getUsername() +
                ", post=" + post.getId() +
                '}';
    }

    @Override
    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append("## ")
                .append(user.getUsername())
                .append('\n');

        String bottom = "-".repeat(10);
        builder.append(bottom)
                .append('\n')
                .append(body)
                .append('\n')
                .append(modifyDate);
        return builder.toString();
    }
}
