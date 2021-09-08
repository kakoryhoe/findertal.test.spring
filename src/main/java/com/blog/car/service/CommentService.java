package com.blog.car.service;

import com.blog.car.domain.Comment;
import com.blog.car.domain.User;
import com.blog.car.repository.CommentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getComment(long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentByAuthor(User author) {
        return commentRepository.findByAuthor(author);
    }

    //    public List<Comment> getCommentByCar(Car car){
    //        return commentRepository.findByCar(car);
    //    }

    public List<Comment> getComments() {
        return (List<Comment>) commentRepository.findAll();
    }

    public Comment postComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
