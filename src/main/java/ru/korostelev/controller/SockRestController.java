package ru.korostelev.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.korostelev.entity.Sock;
import ru.korostelev.service.SockServiceImp;

import java.util.List;

@RestController
@RequestMapping("/api/socks/")
@RequiredArgsConstructor
public class SockRestController {

    private final SockServiceImp sockService;

    @GetMapping
    public List<Sock> findProducts(@RequestParam(name = "filter", required = false) String filter
//                                   @RequestParam(name = "filter", required = false) String filter2
                                   ) {
        return this.sockService.findAllSocks(filter);
    }




}
