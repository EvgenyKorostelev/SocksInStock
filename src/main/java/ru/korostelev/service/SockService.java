package ru.korostelev.service;

import ru.korostelev.entity.Color;
import ru.korostelev.entity.Sock;

import java.util.List;

public interface SockService {

    List<Sock> findAllSocks(String filter);

    Sock createSock(String color, Integer pieces);

    Sock findSockById(Integer id);

    void updateSock(Integer id, String color,Integer pieces);

    void deleteSockById(Integer id);
}
