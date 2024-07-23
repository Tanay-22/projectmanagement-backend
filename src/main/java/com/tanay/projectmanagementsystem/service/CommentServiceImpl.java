package com.tanay.projectmanagementsystem.service;

import com.tanay.projectmanagementsystem.model.Comment;
import com.tanay.projectmanagementsystem.model.Issue;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.CommentRepository;
import com.tanay.projectmanagementsystem.repository.IssueRepository;
import com.tanay.projectmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService
{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String comment) throws Exception
    {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issueOptional.isEmpty())
            throw new Exception("ISSUE NOT FOUND WITH ID - " + issueId);
        if(userOptional.isEmpty())
            throw new Exception("USER NOT FOUND WITH ID - " + userId);

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setIssue(issue);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setContent(comment);

        Comment savedComment = commentRepository.save(newComment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception
    {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if(userOptional.isEmpty())
            throw new Exception("USER NOT FOUND WITH ID - " + userId);
        if(commentOptional.isEmpty())
            throw new Exception("COMMENT NOT FOUND WITH ID - " + commentId);

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user))
            commentRepository.delete(comment);
        else
            throw new Exception("USER DOES NOT HAVE PERMISSION TO DELETE THIS COMMENT");
    }

    @Override
    public List<Comment> findByIssueId(Long issueId)
    {
        return commentRepository.findCommentsByissueId(issueId);
    }
}
