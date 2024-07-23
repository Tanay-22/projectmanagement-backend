package com.tanay.projectmanagementsystem.repository;

import com.tanay.projectmanagementsystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long>
{
    Invitation findByToken(String token);

    Invitation findByEmail(String userEmail);
}
