package com.Kalium.model.contactMessageEntities;

import com.Kalium.model.enums.SubjectEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContactMessageBindingModel {
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters!")
    @NotNull
    private String fullName;

    @Email
    @NotNull(message = "You must enter an email!")
    private String email;

    @NotNull(message = "You must select a subject!")
    private SubjectEnum subject;

    @NotNull(message = "You must enter a message!")
    @Size(min = 2, max = 200, message = "Message must be between 2 and 200 characters!")
    private String message;

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
}
