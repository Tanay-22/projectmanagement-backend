package com.tanay.projectmanagementsystem.repository;

import com.tanay.projectmanagementsystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>
{

}
