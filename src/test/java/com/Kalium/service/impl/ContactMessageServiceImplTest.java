package com.Kalium.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Kalium.model.contactMessageEntities.ContactMessage;
import com.Kalium.model.contactMessageEntities.ContactMessageBindingModel;
import com.Kalium.model.contactMessageEntities.ContactMessageSubjectDTO;
import com.Kalium.model.enums.SubjectEnum;
import com.Kalium.repo.ContactMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ContactMessageServiceImplTest {

    private ContactMessageServiceImpl contactMessageService;
    private ContactMessageRepository contactMessageRepositoryMock;

    @BeforeEach
    void setUp() {
        contactMessageRepositoryMock = mock(ContactMessageRepository.class);
        contactMessageService = new ContactMessageServiceImpl(contactMessageRepositoryMock);
    }

    @Test
    void addMessage_ShouldSaveContactMessage() {
        ContactMessageBindingModel contactMessageBindingModel = new ContactMessageBindingModel();
        contactMessageBindingModel.setFullName("John Doe");
        contactMessageBindingModel.setEmail("john.doe@example.com");
        contactMessageBindingModel.setMessage("Test message");

        boolean result = contactMessageService.addMessage(contactMessageBindingModel);

        assertTrue(result);

        verify(contactMessageRepositoryMock, times(1)).save(any(ContactMessage.class));
    }

    @Test
    void getMessageViewData_ShouldReturnContactMessageSubjectDTO() {
        ContactMessage message1 = new ContactMessage();
        message1.setSubject(SubjectEnum.ACCOUNT);
        message1.setMessage("Test message 1");

        ContactMessage message2 = new ContactMessage();
        message2.setSubject(SubjectEnum.PRODUCT);
        message2.setMessage("Test message 2");

        when(contactMessageRepositoryMock.findAll()).thenReturn(Arrays.asList(message1, message2));

        ContactMessageSubjectDTO result = contactMessageService.getMessageViewData();

        assertNotNull(result);
        assertNotNull(result.getAllMessages());
        assertEquals(2, result.getAllMessages().size());
        assertEquals(1, result.getAccountMessages().size());
        assertEquals(1, result.getProductMessages().size());
        assertEquals(0, result.getDeliveryMessages().size());
        assertEquals(0, result.getGeneralQuestionMessages().size());
        assertEquals(0, result.getOtherMessages().size());
    }
}