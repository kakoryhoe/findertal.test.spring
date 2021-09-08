package com.blog.car.domain;

import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mark;
    private String model;
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Nullable
    private List<Comment> comments;

    public Car() {}

    public Car(String mark, String model, Double price, List<Comment> comments) {
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.comments = comments;
    }

    public Car(String mark, String model, Double price) {
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
