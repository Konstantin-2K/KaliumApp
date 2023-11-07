package com.Kalium.model.contactMessageEntities;

import java.util.List;

public class ContactMessageSubjectDTO {
    private List<ContactMessageDTO> allMessages;

    private List<ContactMessageDTO> accountMessages;

    private List<ContactMessageDTO> productMessages;

    private List<ContactMessageDTO> deliveryMessages;

    private List<ContactMessageDTO> generalQuestionMessages;

    private List<ContactMessageDTO> otherMessages;

    public ContactMessageSubjectDTO(List<ContactMessageDTO> allMessages, List<ContactMessageDTO> accountMessages, List<ContactMessageDTO> productMessages, List<ContactMessageDTO> deliveryMessages, List<ContactMessageDTO> generalQuestionMessages, List<ContactMessageDTO> otherMessages) {
        this.allMessages = allMessages;
        this.accountMessages = accountMessages;
        this.productMessages = productMessages;
        this.deliveryMessages = deliveryMessages;
        this.generalQuestionMessages = generalQuestionMessages;
        this.otherMessages = otherMessages;
    }

    public List<ContactMessageDTO> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<ContactMessageDTO> allMessages) {
        this.allMessages = allMessages;
    }

    public List<ContactMessageDTO> getAccountMessages() {
        return accountMessages;
    }

    public void setAccountMessages(List<ContactMessageDTO> accountMessages) {
        this.accountMessages = accountMessages;
    }

    public List<ContactMessageDTO> getProductMessages() {
        return productMessages;
    }

    public void setProductMessages(List<ContactMessageDTO> productMessages) {
        this.productMessages = productMessages;
    }

    public List<ContactMessageDTO> getDeliveryMessages() {
        return deliveryMessages;
    }

    public void setDeliveryMessages(List<ContactMessageDTO> deliveryMessages) {
        this.deliveryMessages = deliveryMessages;
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
