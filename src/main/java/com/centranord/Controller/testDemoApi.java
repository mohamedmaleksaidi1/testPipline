package com.centranord.Controller;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor

public class testDemoApi {



    @GetMapping("/Get")
    @PreAuthorize("hasAuthority('READ_DEMO')")
    public String get() {
        return "GET::  controllerFromTestDemo";
    }
    @PostMapping("/Post")
    @PreAuthorize("hasAuthority('Post_DEMO')")
    public String post() {
        return "POST::  controllerFromTestDemo";
    }

    @PutMapping("/Put")
    @PreAuthorize("hasAuthority('Put_DEMO')")
    public String put() {
        return "PUT::  controllerFromTestDemo";
    }


    @DeleteMapping("/Delete")
    @PreAuthorize("hasAuthority('Delete_DEMO')")
    public String delete() {
        return "DELETE::  controllerFromTestDemo";
    }


}



