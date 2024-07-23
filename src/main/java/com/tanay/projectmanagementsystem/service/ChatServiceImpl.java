package com.tanay.projectmanagementsystem.service;

import com.tanay.projectmanagementsystem.model.Chat;
import com.tanay.projectmanagementsystem.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService
{
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat)
    {
        return chatRepository.save(chat);
    }
}
