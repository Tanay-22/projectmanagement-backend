package com.tanay.projectmanagementsystem.controller;

import com.tanay.projectmanagementsystem.config.JwtConstant;
import com.tanay.projectmanagementsystem.model.Comment;
import com.tanay.projectmanagementsystem.model.Issue;
import com.tanay.projectmanagementsystem.model.Message;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.UserService;
import com.tanay.projectmanagementsystem.request.CreateCommentRequest;
import com.tanay.projectmanagementsystem.response.MessageResponse;
import com.tanay.projectmanagementsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest req,
                                                 @RequestHeader(JwtConstant.JWT_HEADER) String jwt)
        throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(req.getIssueId(), user.getId(),
                req.getContent());

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
                                                         @RequestHeader(JwtConstant.JWT_HEADER) String jwt)
        throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("COMMENT DELETED SUCCESSFULLY");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId)
    {
        List<Comment> comments = commentService.findByIssueId(issueId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
