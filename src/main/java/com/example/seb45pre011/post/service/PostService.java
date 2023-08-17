package com.example.seb45pre011.post.service;

import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.exception.BusinessLogicException;
import com.example.seb45pre011.exception.ExceptionCode;
import com.example.seb45pre011.post.entity.Post;
import com.example.seb45pre011.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public Post findVerifiedPost(long postId) {
        Optional<Post> optionalComment = postRepository.findById(postId);
        Post findPost =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        return findPost;
    }
}
