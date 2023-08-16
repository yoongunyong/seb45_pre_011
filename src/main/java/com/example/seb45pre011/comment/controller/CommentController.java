package com.example.seb45pre011.comment.controller;

import com.example.seb45pre011.comment.dto.CommentDto;
import com.example.seb45pre011.comment.entity.Comment;
import com.example.seb45pre011.comment.mapper.CommentMapper;
import com.example.seb45pre011.comment.service.CommentService;
import com.example.seb45pre011.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{post-id}/comments")
@Validated
public class CommentController {
    // TODO : 후에 post와 user가 완성되면 user검증과 post검증로직도 들어가야한다.
    private final CommentService commentService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper1) {
        this.commentService = commentService;

        this.mapper = mapper1;
    }

    @PostMapping
    public ResponseEntity postComment(
            @PathVariable("post-id") Long postId,
            @RequestBody CommentDto.PostDto postDto) {
        System.out.println(postDto.getContent());
        Comment comment = commentService.createComment(mapper.commentPostDtoToComment(postDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(
            @PathVariable("post-id") Long post_id,
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentDto.PatchDto patchDto) {

        // 여기서 patchDto를 사용하여 댓글 업데이트 로직을 구현하세요.
        // comment_id를 사용하여 해당 댓글을 찾고, patchDto의 내용으로 업데이트합니다.
        patchDto.setCommentId(commentId);
        //Comment updatedComment = ... // 댓글 업데이트 로직 구현
        Comment updatedComment = commentService.updateComment(mapper.commentPatchDtoToComment(patchDto));
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.commentToCommentResponse(updatedComment)), HttpStatus.OK);

    }
//    @GetMapping
//    public ResponseEntity<List<Comment>> getComments(@RequestParam(value = "cursor", required = false) String cursor,
//                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        List<Comment> comments = commentService.findComments(cursor,pageSize);
//        if (!comments.isEmpty()) {
//            String newCursor = comments.get(comments.size() - 1).getCommentId().toString();
//            // 새로운 커서 값을 생성하여 다음 페이지의 데이터를 가져오기 위해 사용
//            return ResponseEntity.ok()
//                    .header("X-Next-Cursor", newCursor)
//                    .body(comments);
//        } else {
//            return ResponseEntity.noContent().build();
//        }
//    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(
            @PathVariable("post-id") Long postId,
            @PathVariable("comment-id") Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}