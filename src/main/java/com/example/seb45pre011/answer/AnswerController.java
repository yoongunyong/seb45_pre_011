package com.example.seb45pre011.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{post_id}/answers")
public class AnswerController {
  @Autowired
  private AnswerService answerService;

  @PostMapping
  public ResponseEntity<Answer> createAnswer(@PathVariable("post_id") Long postId, @RequestBody AnswerDto answerDto) {
    Answer createdAnswer = answerService.addAnswer(postId, answerDto);
    if (createdAnswer != null) {
      return ResponseEntity.ok(createdAnswer);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{answer_id}")
  public ResponseEntity<Answer> getAnswerById(@PathVariable("answer_id") Long answerId) {
    Answer answer = answerService.getAnswerById(answerId);
    if (answer != null) {
      return ResponseEntity.ok(answer);
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{answer_id}")
  public ResponseEntity<Answer> updateAnswer(
          @PathVariable("answer_id") Long answerId,
          @RequestBody AnswerDto updatedAnswerDto) {
    Answer updatedAnswer = answerService.updateAnswer(answerId, updatedAnswerDto);
    if (updatedAnswer != null) {
      return ResponseEntity.ok(updatedAnswer);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{answer_id}")
  public ResponseEntity<Void> deleteAnswer(@PathVariable("answer_id") Long answerId) {
    answerService.deleteAnswer(answerId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<Answer>> getAllAnswersByPost(@PathVariable("post_id") Long postId) {
    List<Answer> answers = answerService.getAllAnswersByPost(postId);
    return ResponseEntity.ok(answers);
  }
}
