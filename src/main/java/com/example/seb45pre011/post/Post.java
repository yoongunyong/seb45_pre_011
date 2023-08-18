package com.example.seb45pre011.post;

import com.example.seb45pre011.comment.Comment;
import com.example.seb45pre011.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long postId;

  @NotBlank
  private String title;

  @NotBlank
  private String content;

  private int views = 0;
  private int upVotes = 0;
  private int downVotes = 0;
  private int reports = 0;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToMany
  @JoinTable(name = "post_tags",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

//  @ManyToMany
//  @JoinColumn(name = "member_id")
//  private Menber member;
//
//  @OneToMany(mappedBy = "post")
//  private Set<Comment> comments = new HashSet<>();

}
