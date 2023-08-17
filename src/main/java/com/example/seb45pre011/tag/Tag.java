package com.example.seb45pre011.tag;

import com.example.seb45pre011.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tag_id")
  private Long tagId;

  private String name;
  private String description;

  @ManyToMany(mappedBy = "tags")
  private Set<Post> posts = new HashSet<>();
}
