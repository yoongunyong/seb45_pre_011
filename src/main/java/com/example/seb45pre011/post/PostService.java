package com.example.seb45pre011.post;

import com.example.seb45pre011.member.Member;

import java.util.List;

public interface PostService {
  Post createPost(PostDto postDto);
  Post updatePost(Long postId, PostDto updatedPostDto);
  void deletePost(Long postId);
  Post getPostById(Long postId);
  List<Post> getAllPosts();
  int getUserPostCount(Member member);
}
