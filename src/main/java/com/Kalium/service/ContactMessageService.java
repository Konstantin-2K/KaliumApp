package com.Kalium.service;

import com.Kalium.model.contactMessageEntities.ContactMessageBindingModel;
import com.Kalium.model.contactMessageEntities.ContactMessageSubjectDTO;

public interface ContactMessageService {
    boolean addMessage(ContactMessageBindingModel contactMessageBindingModel);

    ContactMessageSubjectDTO getMessageViewData();
}
