package com.Kalium.model.contactMessageEntities;

import java.util.List;

public class ContactMessageSubjectDTO {
    private List<ContactMessageDTO> allMessages;

    private List<ContactMessageDTO> accountIssueMessages;

    private List<ContactMessageDTO> productIssueMessages;

    private List<ContactMessageDTO> deliveryIssueMessages;

    private List<ContactMessageDTO> generalQuestionMessages;

    private List<ContactMessageDTO> otherMessages;

    public ContactMessageSubjectDTO(List<ContactMessageDTO> allMessages, List<ContactMessageDTO> accountIssueMessages, List<ContactMessageDTO> productIssueMessages, List<ContactMessageDTO> deliveryIssueMessages, List<ContactMessageDTO> generalQuestionMessages, List<ContactMessageDTO> otherMessages) {
        this.allMessages = allMessages;
        this.accountIssueMessages = accountIssueMessages;
        this.productIssueMessages = productIssueMessages;
        this.deliveryIssueMessages = deliveryIssueMessages;
        this.generalQuestionMessages = generalQuestionMessages;
        this.otherMessages = otherMessages;
    }

    public List<ContactMessageDTO> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<ContactMessageDTO> allMessages) {
        this.allMessages = allMessages;
    }

    public List<ContactMessageDTO> getAccountIssueMessages() {
        return accountIssueMessages;
    }

    public void setAccountIssueMessages(List<ContactMessageDTO> accountIssueMessages) {
        this.accountIssueMessages = accountIssueMessages;
    }

    public List<ContactMessageDTO> getProductIssueMessages() {
        return productIssueMessages;
    }

    public void setProductIssueMessages(List<ContactMessageDTO> productIssueMessages) {
        this.productIssueMessages = productIssueMessages;
    }

    public List<ContactMessageDTO> getDeliveryIssueMessages() {
        return deliveryIssueMessages;
    }

    public void setDeliveryIssueMessages(List<ContactMessageDTO> deliveryIssueMessages) {
        this.deliveryIssueMessages = deliveryIssueMessages;
    }

    public List<ContactMessageDTO> getGeneralQuestionMessages() {
        return generalQuestionMessages;
    }

    public void setGeneralQuestionMessages(List<ContactMessageDTO> generalQuestionMessages) {
        this.generalQuestionMessages = generalQuestionMessages;
    }

    public List<ContactMessageDTO> getOtherMessages() {
        return otherMessages;
    }

    public void setOtherMessages(List<ContactMessageDTO> otherMessages) {
        this.otherMessages = otherMessages;
    }
}
