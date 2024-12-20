package ru.korostelev.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korostelev.controller.payload.NewSockPayload;
import ru.korostelev.controller.payload.UpdateSockPayload;
import ru.korostelev.entity.Sock;
import ru.korostelev.service.SockServiceImp;

import java.util.*;


@RestController
@RequestMapping("/api/socks/")
@RequiredArgsConstructor
public class SockRestController {

    private final SockServiceImp sockService;
    private final MessageSource messageSource;

//    @ModelAttribute("sock")
//    public Sock getSockById(@PathVariable("id") Integer id) {
//        return this.sockService.findSockById(id)
//                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.sock.not_found"));
//    }
//
//    @ModelAttribute("sock")
//    public List<Sock> getSockByColor(String color) {
//        return this.sockService.findSockByColor(color)
//                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.sock.not_found"));
//    }


    //    Получение общего количества носков с фильтрацией:
//    GET /api/socks
//    Фильтры:
//    Цвет носков.
//    Оператор сравнения (moreThan, lessThan, equal).
//    Процент содержания хлопка.
//    Возвращает количество носков, соответствующих критериям.
    @GetMapping
    public List<Sock> findSocks(@RequestParam(name = "filterColor", required = false) String filterColor,
                                @RequestParam(name = "filterCotton", required = false) String filterCotton,
                                @RequestParam(name = "comparisonOperator", required = false) String comparisonOperator
    ) {
        return this.sockService.findAllSocks(filterColor, filterCotton, comparisonOperator);
    }

    //    Регистрация прихода носков:
//    POST /api/socks/income
//    Параметры: цвет носков, процентное содержание хлопка, количество.
//    Увеличивает количество носков на складе.

    @PostMapping("income")
    public ResponseEntity<?> incomeSock(@Valid @RequestBody NewSockPayload payload,
                                        BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Optional<Sock> tempSock = sockService.findSockByColor(payload.color()).get().stream().filter(sock ->
                    Objects.equals(sock.getPercentageCotton(), payload.percentageCotton())).findAny();
            if (tempSock.isEmpty()) {
                this.sockService.createSock(
                        payload.color(), payload.percentageCotton(), payload.pieces());
            } else {
                this.sockService.updateSock(tempSock.get().getId(), payload.color(), payload.percentageCotton(),
                        tempSock.get().getPieces() + payload.pieces());
            }
            return ResponseEntity.noContent()
                    .build();
        }
    }

    //    Регистрация отпуска носков:
//    POST /api/socks/outcome
//    Параметры: цвет носков, процентное содержание хлопка, количество.
//    Уменьшает количество носков на складе, если их хватает.
    @PostMapping("outcome")
    public ResponseEntity<?> outcomeSock(@Valid @RequestBody UpdateSockPayload payload,
                                         BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Sock sock = sockService.findSockByColor(payload.color()).get().stream().filter(o ->
                    Objects.equals(o.getPercentageCotton(), payload.percentageCotton())).toList().get(0);
            this.sockService.updateSock(sock.getId(),
                    payload.color(), payload.percentageCotton(), payload.pieces());
            return ResponseEntity.noContent()
                    .build();
        }
    }


    //    Обновление данных носков:
//    PUT /api/socks/{id}.
//    Позволяет изменить параметры носков (цвет, процент хлопка, количество).
    @PutMapping("{id:\\d+}")
    public ResponseEntity<?> updateSock(@PathVariable("id") Integer id,
                                        @Valid @RequestBody UpdateSockPayload payload,
                                        BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.sockService.updateSock(id, payload.color(), payload.percentageCotton(), payload.pieces());
            return ResponseEntity.noContent()
                    .build();
        }
    }


    //    Загрузка партий носков из Excel или CSV (один формат на выбор) файла:
//    POST /api/socks/batch
//    Принимает Excel или CSV (один формат на выбор) файл с партиями носков, содержащими цвет,
//    процентное содержание хлопка и количество.
    @PostMapping("batch")
    public ResponseEntity<?> loadingBatches(@RequestParam(name = "path") String path) {
        try {
            sockService.loadingBatchesSocksFromFile(path);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    Ошибка не найдено элементов
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }

}
