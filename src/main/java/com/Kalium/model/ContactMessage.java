package com.Kalium.model;

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SubjectEnum getSubject() {
        return subject;
    }

    public void setSubject(SubjectEnum subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(LocalDateTime dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }
}
