package com.example.seb45pre011.post.entity;

import com.example.seb45pre011.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private int viewCount = 0;
    private int upVotes = 0;
    private int downVotes = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

//    @ManyToMany
//    @JoinTable(
//            name = "post_tag",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id")
//    )
//    private List<Tag> tags = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public void incrementUpVotes() {
        upVotes++;
    }

    public void incrementDownVotes() {
        downVotes++;
    }
}