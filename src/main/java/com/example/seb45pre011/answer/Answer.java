package com.example.seb45pre011.answer;

import com.example.seb45pre011.member.Member;
import com.example.seb45pre011.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long answerId;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_post_id")
  private Post parentPost;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToMany
  @JoinTable(name = "answer_memberss",
          joinColumns = @JoinColumn(name = "answer_id"),
          inverseJoinColumns = @JoinColumn(name = "member_id"))
  private Set<Member> members = new HashSet<>();
}