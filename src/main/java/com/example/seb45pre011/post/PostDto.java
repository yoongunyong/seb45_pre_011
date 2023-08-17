package com.example.seb45pre011.post;

import com.example.seb45pre011.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class PostDto {
  private Long id;
  private String title;
  private String content;
  private int views;
  private int likes;
  private int dislikes;
  private int reports;
  private LocalDateTime createdAt;
  private Set<Tag> tags;
}