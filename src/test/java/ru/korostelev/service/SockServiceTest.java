package ru.korostelev.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.korostelev.entity.Sock;
import ru.korostelev.repository.SockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты SockServiceImp")
public class SockServiceTest {

    @Mock
    SockRepository sockRepository;

    @InjectMocks
    SockServiceImp sockServiceImp;

    @Test
    @DisplayName("createSock создаст и вернет новые носки")
    void createSockTest() {

        String newColorPayload = "Orange";
        Integer newPercentageCottonPayload = 40;
        Integer newPiecesPayload = 10;
        Sock expectedSock = new Sock(null, newColorPayload, newPercentageCottonPayload, newPiecesPayload);

        when(sockRepository.save(expectedSock)).thenReturn(expectedSock);
        Sock actualSock = sockServiceImp.createSock(
                expectedSock.getColor(), expectedSock.getPercentageCotton(), expectedSock.getPieces());

        assertEquals(expectedSock, actualSock);
    }


    @Test
    @DisplayName("findSockById найдет носки по id и вернет обернутые в Optional")
    void findSockByIdTest() {

        Sock expectedSock = new Sock(1, "White", 100, 1);

        when(sockRepository.findById(expectedSock.getId())).thenReturn(Optional.of(expectedSock));
        Optional<Sock> actualSock = sockServiceImp.findSockById(expectedSock.getId());

        assertEquals(expectedSock, actualSock.get());
    }

    @Test
    @DisplayName("findSockByColor найдет все носки определенного цвета и вернет лист, завернутый в Optional")
    void findSockByColor() {

        List<Sock> expectedSocksList = new ArrayList<>(List.of(
                new Sock(1, "Red", 50, 11),
                new Sock(2, "Blue", 34, 45),
                new Sock(3, "Red", 40, 33),
                new Sock(4, "Orange", 80, 56)));

        when(sockRepository.findAllByColorLikeIgnoreCase("Red")).thenReturn(expectedSocksList);
        List<Sock> actualSocksList = sockServiceImp.findSockByColor("Red").get();

        assertEquals(expectedSocksList, actualSocksList);
    }

    @Test
    @DisplayName("updateSock изменяет информацию о носках ничего не возвращает")
    void updateSock() {

        Sock updateSock = new Sock(1, "Red", 80, 1);
        String updateColorPayload = "Orange";
        Integer updatePercentageCottonPayload = 40;
        Integer updatePiecesPayload = 10;

        when(sockRepository.findById(updateSock .getId())).thenReturn(Optional.of(updateSock));
        Sock expectedSock = sockServiceImp.findSockById(updateSock.getId()).get();
        expectedSock.setColor(updateColorPayload);
        expectedSock.setPercentageCotton(updatePercentageCottonPayload);
        expectedSock.setPieces(updatePiecesPayload);
        sockServiceImp.updateSock(
                updateSock.getId(), updateColorPayload, updatePercentageCottonPayload, updatePiecesPayload);
        Sock actualSock = sockServiceImp.findSockById(updateSock.getId()).get();

        assertEquals(expectedSock, actualSock);
    }

}
