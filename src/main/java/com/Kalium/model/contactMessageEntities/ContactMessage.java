package com.Kalium.model.contactMessageEntities;

import com.Kalium.model.BaseEntity;
import com.Kalium.model.enums.SubjectEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage extends BaseEntity {
    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String fullName;

    @Email
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SubjectEnum subject;

    @NotNull
    @Size(min = 2, max = 200)
    private String message;

    @NotNull
    @PastOrPresent
    private LocalDateTime dateTimeSent;

    public String getFullName() {
        return fullName;
    }

    public ContactMessage setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactMessage setEmail(String email) {
        this.email = email;
        return this;
    }

    public SubjectEnum getSubject() {
        return subject;
    }

    public ContactMessage setSubject(SubjectEnum subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public ContactMessage setDateTimeSent(LocalDateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
        return this;
    }
}
