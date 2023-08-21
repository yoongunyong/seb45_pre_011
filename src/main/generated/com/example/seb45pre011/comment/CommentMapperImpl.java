package com.example.seb45pre011.comment;

import com.example.seb45pre011.comment.CommentDto.PatchDto;
import com.example.seb45pre011.comment.CommentDto.PostDto;
import com.example.seb45pre011.comment.CommentDto.Response;
import com.example.seb45pre011.member.Member;
import com.example.seb45pre011.post.Post;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-20T18:55:01+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.19 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( postDto.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(PatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setCommentId( patchDto.getCommentId() );
        comment.setContent( patchDto.getContent() );

        return comment;
    }

    @Override
    public Response commentToResponseDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        Long postId = null;
        Long userId = null;
        long commentId = 0L;
        String content = null;
        Date createdAt = null;

        postId = commentPostPostId( comment );
        userId = commentMemberUserId( comment );
        if ( comment.getCommentId() != null ) {
            commentId = comment.getCommentId();
        }
        content = comment.getContent();
        createdAt = comment.getCreatedAt();

        Response response = new Response( commentId, content, createdAt, userId, postId );

        return response;
    }

    private Long commentPostPostId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Post post = comment.getPost();
        if ( post == null ) {
            return null;
        }
        Long postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }

    private Long commentMemberUserId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Member member = comment.getMember();
        if ( member == null ) {
            return null;
        }
        long userId = member.getUserId();
        return userId;
    }
}
