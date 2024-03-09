package com.centranord.Controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Email")
public class EmailController {
    @GetMapping
    @PreAuthorize("hasAuthority('Email:read')")
    public String get() {
        return "GET:: Composant_controllerEmail";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('Email:create')")

    public String post() {
        return "POST:: Composant_controllerEmail";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('Email:update')")

    public String put() {
        return "PUT:: Composant_controllerEmail";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('Email:delete')")

    public String delete() {
        return "DELETE:: Composant_controllerEmail";
    }

}
