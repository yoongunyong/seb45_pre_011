package com.example.seb45pre011.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")

public class PostController {
  @Autowired
  private PostService postService;

  @PostMapping
  public ResponseEntity<Post> createPost(@RequestBody PostDto postDto) {
    Post createdPost = postService.createPost(postDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
  }

  @PutMapping("/{post_id}")
  public ResponseEntity<Post> updatePost(@PathVariable("post_id") Long postId, @RequestBody PostDto updatedPostDto) {
    Post updated = postService.updatePost(postId, updatedPostDto);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{post_id}")
  public ResponseEntity<Void> deletePost(@PathVariable("post_id") Long postId) {
    postService.deletePost(postId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{post_id}")
  public ResponseEntity<Post> getPostById(@PathVariable("post_id") Long postId) {
    Post post = postService.getPostById(postId);
    if (post != null) {
      return ResponseEntity.ok(post);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<Post>> getAllPosts() {
    List<Post> posts = postService.getAllPosts();
    return ResponseEntity.ok(posts);
  }
}