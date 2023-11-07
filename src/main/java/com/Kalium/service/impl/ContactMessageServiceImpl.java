package com.Kalium.service.impl;

import com.Kalium.model.contactMessageEntities.ContactMessage;
import com.Kalium.model.contactMessageEntities.ContactMessageBindingModel;
import com.Kalium.model.contactMessageEntities.ContactMessageDTO;
import com.Kalium.model.contactMessageEntities.ContactMessageSubjectDTO;
import com.Kalium.repo.ContactMessageRepository;
import com.Kalium.service.ContactMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageServiceImpl(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Override
    public boolean addMessage(ContactMessageBindingModel contactMessageBindingModel) {
        ContactMessage contactMessage = map(contactMessageBindingModel);
        contactMessageRepository.save(contactMessage);
        return true;
    }

    @Override
    public ContactMessageSubjectDTO getMessageViewData() {
        List<ContactMessageDTO> allMessages = contactMessageRepository.findAll()
                .stream().map(ContactMessageDTO::createFromMessage)
                .toList();

        List<ContactMessageDTO> accountMessages = new ArrayList<>();
        List<ContactMessageDTO> productMessages = new ArrayList<>();
        List<ContactMessageDTO> deliveryMessages = new ArrayList<>();
        List<ContactMessageDTO> generalQuestionMessages = new ArrayList<>();
        List<ContactMessageDTO> otherMessages = new ArrayList<>();

        for (ContactMessageDTO contactMessageDTO : allMessages) {
            switch (contactMessageDTO.getSubject()) {
                case ACCOUNT -> accountMessages.add(contactMessageDTO);
                case PRODUCT -> productMessages.add(contactMessageDTO);
                case DELIVERY -> deliveryMessages.add(contactMessageDTO);
                case GENERAL_QUESTION -> generalQuestionMessages.add(contactMessageDTO);
                case OTHER -> otherMessages.add(contactMessageDTO);
            }
        }

        return new ContactMessageSubjectDTO(allMessages, accountMessages, productMessages, deliveryMessages, generalQuestionMessages, otherMessages);
    }

    private ContactMessage map(ContactMessageBindingModel contactMessageBindingModel) {
        return new ContactMessage()
                .setFullName(contactMessageBindingModel.getFullName())
                .setEmail(contactMessageBindingModel.getEmail())
                .setSubject(contactMessageBindingModel.getSubject())
                .setMessage(contactMessageBindingModel.getMessage())
                .setDateTimeSent(LocalDateTime.now());

    }
}
