package com.Kalium.model.contactMessageEntities;

import com.Kalium.model.enums.SubjectEnum;

import java.time.LocalDateTime;

public class ContactMessageDTO {
    private String fullName;

    private String email;

    private SubjectEnum subject;

    private String message;

    private LocalDateTime dateTimeSent;

    public ContactMessageDTO() {
    }

    public ContactMessageDTO(String fullName, String email, SubjectEnum subject, String message, LocalDateTime dateTimeSent) {
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.dateTimeSent = dateTimeSent;
    }

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

    public static ContactMessageDTO createFromMessage(ContactMessage contactMessage) {
        ContactMessageDTO contactMessageDTO = new ContactMessageDTO();
        contactMessageDTO.setFullName(contactMessage.getFullName());
        contactMessageDTO.setEmail(contactMessage.getEmail());
        contactMessageDTO.setSubject(contactMessage.getSubject());
        contactMessageDTO.setMessage(contactMessage.getMessage());
        contactMessageDTO.setDateTimeSent(contactMessage.getDateTimeSent());
        return contactMessageDTO;
    }

}
