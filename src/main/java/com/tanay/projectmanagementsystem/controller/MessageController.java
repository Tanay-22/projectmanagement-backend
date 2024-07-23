package com.tanay.projectmanagementsystem.controller;

import com.tanay.projectmanagementsystem.model.Chat;
import com.tanay.projectmanagementsystem.model.Message;
import com.tanay.projectmanagementsystem.model.Project;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.UserService;
import com.tanay.projectmanagementsystem.request.CreateMessageRequest;
import com.tanay.projectmanagementsystem.service.MessageService;
import com.tanay.projectmanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController
{
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req) throws Exception
    {
        User user = userService.findUserById(req.getSenderId());

        if(user == null)
            throw new Exception("USER NOT FOUND WITH ID - " + req.getSenderId());

        Chat chat = projectService.getChatByProjectId(req.getProjectId());

        if(chat == null)
            throw new Exception("CHATS NOT FOUND");

        Message sentMessage = messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());

        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception
    {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);

        return ResponseEntity.ok(messages);
    }
}
