package com.tanay.projectmanagementsystem.service;

import com.tanay.projectmanagementsystem.model.Issue;
import com.tanay.projectmanagementsystem.model.Project;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.IssueRepository;
import com.tanay.projectmanagementsystem.repository.UserService;
import com.tanay.projectmanagementsystem.request.IssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService
{
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception
    {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent())
            return issue.get();
        throw new Exception("No issues found with issue id - " + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception
    {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest req, User user) throws Exception
    {
        Project project = projectService.getProjectById(req.getProjectId());

        Issue issue = new Issue();
        issue.setTitle(req.getTitle());
        issue.setDescription(req.getDescription());
        issue.setStatus(req.getStatus());
        issue.setPriority(req.getPriority());
        issue.setDueDate(req.getDueDate());
        issue.setProject(project);

        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception
    {
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception
    {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);

        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception
    {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);

        return issueRepository.save(issue);
    }
}
