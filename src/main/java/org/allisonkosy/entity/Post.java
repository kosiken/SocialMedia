package org.allisonkosy.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "posts")
public class Post implements Model  {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;
    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "slug", nullable = false)
    private String slug;

    @Transient
    private boolean hasPrinted = false;

    public Long getId() {
        return id;
    }

    public boolean isHasPrinted() {
        return hasPrinted;
    }

    public String getBody() {
        return body;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public User getUser() {
        return user;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHasPrinted(boolean hasPrinted) {
        this.hasPrinted = hasPrinted;
    }

    public String getSlug() {
        return slug;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setSlug(String slug) {
       String s = slug.toLowerCase(Locale.ROOT).replace(' ', '-');

        this.slug = s;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user= " + user.getUsername() + "user_id=" + user.getId() +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", body='" + body.substring(0, 4) + "...." + '\'' +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }

    @Override
    public String describe() {
        if (hasPrinted) return "";
        hasPrinted = true;

        StringBuilder builder = new StringBuilder();

        builder.append("# ").append(title)
        .append("\n-> ").append(modifyDate)
                .append('\n')
                .append(body)
                .append("\n\n");
        String bottom = "-".repeat(10);
        builder.append(bottom);

        builder.append("\nAuthor: ");
        builder.append(user.getUsername())
                .append("\n\n");
        return builder.toString();
    }
}
