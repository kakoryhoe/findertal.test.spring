package com.blog.car.repository;

import com.blog.car.domain.Comment;
import com.blog.car.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment findById(long id);
    List<Comment> findByAuthor(User author);
}
