package com.example.seb45pre011.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String content;
    @Column(name = "createAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
    @PrePersist
    protected void onCreate() {
    createdAt = new Date();
    }
}
