package ru.korostelev.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korostelev.entity.Sock;
import ru.korostelev.repository.SockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SockServiceImp implements SockService {

    private final SockRepository sockRepository;


    @Override
    public List<Sock> findAllSocks(String filter) {
        return List.of();
    }

    @Override
    public Sock createSock(String color, Integer pieces) {
        return null;
    }

    @Override
    public Sock findSockById(Integer id) {
        return null;
    }

    @Override
    public void updateSock(Integer id, String color, Integer pieces) {

    }

    @Override
    public void deleteSockById(Integer id) {

    }
}
