package com.example.seb45pre011.post;

import com.example.seb45pre011.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class PostDto {
  private String title;
  private String content;
}