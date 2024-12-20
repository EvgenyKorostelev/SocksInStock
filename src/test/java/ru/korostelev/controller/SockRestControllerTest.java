package ru.korostelev.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.korostelev.controller.payload.NewSockPayload;
import ru.korostelev.entity.Sock;
import ru.korostelev.repository.SockRepository;
import ru.korostelev.service.SockService;
import ru.korostelev.service.SockServiceImp;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты ProductsController")
public class SockRestControllerTest {

    @InjectMocks
    private SockRestController sockController;

    @Mock
    private SockServiceImp sockService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void incomeSock_ShouldThrowBindException_WhenBindingResultHasErrors() {

        NewSockPayload payload = new NewSockPayload("red", 80, 10);
        when(bindingResult.hasErrors()).thenReturn(true);


        assertThrows(BindException.class, () -> sockController.incomeSock(payload, bindingResult));
        verifyNoInteractions(sockService);
    }

    @Test
    void incomeSock_ShouldCreateNewSock_WhenSockDoesNotExist() throws BindException {

        NewSockPayload payload = new NewSockPayload("blue", 70, 5);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(sockService.findSockByColor(payload.color())).thenReturn(Optional.of(Collections.emptyList()));

        ResponseEntity<?> response = sockController.incomeSock(payload, bindingResult);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(sockService).createSock(payload.color(), payload.percentageCotton(), payload.pieces());
        verify(sockService, never()).updateSock(anyInt(), anyString(), anyInt(), anyInt());
    }

    @Test
    void incomeSock_ShouldUpdateExistingSock_WhenSockExists() throws BindException {
        
        NewSockPayload payload = new NewSockPayload("green", 50, 15);
        Sock existingSock = new Sock(1, "green", 50, 10);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(sockService.findSockByColor(payload.color()))
                .thenReturn(Optional.of(Collections.singletonList(existingSock)));

        ResponseEntity<?> response = sockController.incomeSock(payload, bindingResult);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(sockService).updateSock(existingSock.getId(), payload.color(), payload.percentageCotton(), 25);
        verify(sockService, never()).createSock(anyString(), anyInt(), anyInt());
    }


}
