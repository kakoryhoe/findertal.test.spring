package com.blog.car.domain;

import com.blog.car.domain.User;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    @ManyToOne(optional = false)
    private User author;

    public Comment() {}

    public Comment(String content, User author) {
        this.content = content;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
