package com.example.seb45pre011.post.repository;

import com.example.seb45pre011.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
