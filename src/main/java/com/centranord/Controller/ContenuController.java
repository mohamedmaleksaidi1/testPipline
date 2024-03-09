package com.centranord.Controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contenu")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContenuController {
    @GetMapping()
    @PreAuthorize("hasAuthority('Contenu:read')")
    public String get() {
        return "GET:: Composant_controllerContenu";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('Contenu:create')")

    public String post() {
        return "POST:: Composant_controllerContenu";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('Contenu:update')")
    public String put() {
        return "PUT:: Composant_controllerContenu";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('Contenu:delete')")
    public String delete() {
        return "DELETE:: Composant_controllerContenu";
    }
}
