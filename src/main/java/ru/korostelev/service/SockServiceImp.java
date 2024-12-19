package ru.korostelev.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.korostelev.entity.Sock;
import ru.korostelev.repository.SockRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SockServiceImp implements SockService {

    private final SockRepository sockRepository;


    @Override
    public List<Sock> findAllSocks(String filterColor, String filterCotton, String comparisonOperator) {
        if (!filterColor.isBlank() && filterCotton.isBlank()) {
            return this.sockRepository.findAllByColorLikeIgnoreCase("%" + filterColor + "%");
        } else if (filterColor.isBlank() && !filterCotton.isBlank()) {
            return cottonFilterComparison(
                    this.sockRepository.findAllByColorLikeIgnoreCase("%" + filterColor + "%"),
                    comparisonOperator);
        } else if (!filterColor.isBlank() & !filterCotton.isBlank()) {
            return cottonFilterComparison(
                    this.sockRepository.findAllByColorLikeIgnoreCase("%" + filterColor + "%"),
                    comparisonOperator);
        } else {
            return this.sockRepository.findAll();
        }
    }

    private List<Sock> cottonFilterComparison(List<Sock> socks, String comparisonOperator) {
        return switch (comparisonOperator) {
            case "-1" -> lessThanCotton(socks, comparisonOperator);
            case "0" -> equalCotton(socks, comparisonOperator);
            case "1" -> moreThanCotton(socks, comparisonOperator);
            default -> null;
        };
    }

    private List<Sock> moreThanCotton(List<Sock> socks, String comparisonOperator) {
        return socks.stream().filter(sock ->
                sock.getPercentageCotton() > Integer.parseInt(comparisonOperator)).toList();
    }

    private List<Sock> lessThanCotton(List<Sock> socks, String comparisonOperator) {
        return socks.stream().filter(sock ->
                sock.getPercentageCotton() < Integer.parseInt(comparisonOperator)).toList();
    }

    private List<Sock> equalCotton(List<Sock> socks, String comparisonOperator) {
        return socks.stream().filter(sock ->
                sock.getPercentageCotton() == Integer.parseInt(comparisonOperator)).toList();
    }


    @Override
    @Transactional
    public Sock createSock(String color, Integer percentageCotton, Integer pieces) {
        return this.sockRepository.save(new Sock(null, color, percentageCotton, pieces));
    }

    @Override
    public Optional<Sock> findSockById(Integer id) {
        return sockRepository.findById(id);
    }

    @Override
    public Optional<List<Sock>> findSockByColor(String color) {
        return Optional.ofNullable(sockRepository.findAllByColorLikeIgnoreCase(color));
    }

    @Override
    @Transactional
    public void updateSock(Integer id, String color, Integer percentageCotton, Integer pieces) {
        this.sockRepository.findById(id)
                .ifPresentOrElse(sock -> {
                    sock.setColor(color);
                    sock.setPercentageCotton(percentageCotton);
                    sock.setPieces(pieces);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void loadingBatchesSocksFromFile(String path) {
        List<Sock> socks = readingBatchesSocks(path);
        savingBatchesSocksToBd(socks);
    }

    private List<Sock> readingBatchesSocks(String path) {
        List<Sock> socks = new ArrayList<>();
        String[] socksArr;
        String line;
        try (BufferedReader bfr = new BufferedReader(new FileReader(path))) {
            while ((line = bfr.readLine()) != null) {
                socksArr = line.split(",");
                socks.add(new Sock(null, socksArr[0], Integer.parseInt(socksArr[1]), Integer.parseInt(socksArr[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка, файл не найден!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка при вводе/выводе данных из файла!");
            e.printStackTrace();
        }
        return socks;
    }

    private void savingBatchesSocksToBd(List<Sock> socks) {
        List<Sock> socksInBd = this.sockRepository.findAll();
        for (Sock sockBatche : socks) {
            Optional<Sock> tempSock = socksInBd.stream()
                    .filter(o -> o.getColor().equals(sockBatche.getColor()))
                    .filter(o1 -> Objects.equals(o1.getPercentageCotton(), sockBatche.getPercentageCotton()))
                    .findFirst();
            if (tempSock.isEmpty()) {
                sockRepository.save(new Sock(
                        null, sockBatche.getColor(), sockBatche.getPercentageCotton(), sockBatche.getPieces()));
            } else {
                this.sockRepository.findById(tempSock.get().getId())
                        .ifPresentOrElse(sock -> sock.setPieces(tempSock.get().getPieces() + sockBatche.getPieces())
                                , () -> {
                                    throw new NoSuchElementException();
                                });
            }
        }
    }
}
