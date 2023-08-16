package com.example.seb45pre011.comment.service;

import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.mapper.CommentMapper;
import com.example.seb45pre011.comment.repository.CommentRepository;
import com.example.seb45pre011.exception.BusinessLogicException;
import com.example.seb45pre011.exception.ExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }
    public Comment updateComment(Comment comment){
        //  존재하는 댓글인지 확인
        Comment findComment = findVerifiedComment(comment.getCommentId());
        findComment.setContent(comment.getContent());
        return commentRepository.save(findComment);
    }
    public void deleteComment(long commentId){
        Comment comment = findVerifiedComment(commentId);
        commentRepository.delete(comment);
    }
    public List<Comment> findComments(String cursor, int pageSize)  {
        return commentRepository.findCommentsByCursor(cursor, pageSize);
    }
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

}
