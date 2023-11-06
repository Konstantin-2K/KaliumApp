package com.Kalium.repo;

import com.Kalium.model.contactMessageEntities.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, UUID> {
}
