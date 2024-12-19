package ru.korostelev.service;

import ru.korostelev.entity.Sock;

import java.util.List;
import java.util.Optional;

public interface SockService {

    List<Sock> findAllSocks(String filterColor, String filterCotton, String comparisonOperator);

    Sock createSock(String color, Integer percentageCotton, Integer pieces);

    Optional<Sock> findSockById(Integer id);

    Optional<List<Sock>> findSockByColor(String color);

    void updateSock(Integer id, String color, Integer percentageCotton, Integer pieces);

    void loadingBatchesSocksFromFile(String path);
}
