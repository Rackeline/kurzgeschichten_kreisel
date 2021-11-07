package de.caro_annemie.kurzgeschichten_kreisel.controller;

import org.springframework.web.bind.annotation.RestController;

import de.caro_annemie.kurzgeschichten_kreisel.UserRepository;

@RestController
public class UserController {
    private final UserRepository repository;

    // constructor
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // toDo URL Mappings   

}
