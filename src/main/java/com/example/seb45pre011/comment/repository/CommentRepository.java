package com.example.seb45pre011.comment.repository;

import com.example.seb45pre011.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentsByCursor(String cursor, int pageSize);
}
