package com.tanay.projectmanagementsystem.request;

import com.tanay.projectmanagementsystem.model.Issue;
import lombok.Data;

@Data
public class CreateCommentRequest
{
    private String content;

    private Long issueId;
}
