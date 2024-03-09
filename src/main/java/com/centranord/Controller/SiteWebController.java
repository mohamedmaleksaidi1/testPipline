package com.centranord.Controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/siteweb")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SiteWebController {
    @GetMapping
    @PreAuthorize("hasAuthority('siteweb:read')")
    public String get() {
        return "GET:: Composant_controllerEmail";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('siteweb:create')")

    public String post() {
        return "POST:: Composant_controllerEmail";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('siteweb:update')")

    public String put() {
        return "PUT:: Composant_controllerEmail";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('siteweb:delete')")

    public String delete() {
        return "DELETE:: Composant_controllerEmail";
    }
}
