package ru.korostelev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.korostelev.entity.Sock;

import java.util.List;

public interface SockRepository extends JpaRepository<Sock, Integer> {

    List<Sock> findAllByColorLikeIgnoreCase(String filter);

    List<Sock> findAllByPercentageCottonLikeIgnoreCase(String filter);
}
