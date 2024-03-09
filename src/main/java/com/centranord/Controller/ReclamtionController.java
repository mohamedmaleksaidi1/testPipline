package com.centranord.Controller;



import com.centranord.Entity.Reclamation;
import com.centranord.Services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Reclamation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReclamtionController {

    @Autowired
    private ReclamationService reclamationService;


    @GetMapping
    @PreAuthorize("hasAuthority('Reclamation:read')")
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }
    @PostMapping("/reclamations")
    @PreAuthorize("hasAuthority('Reclamation:Create')")
    public Reclamation ajouterReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.addReclamation(reclamation);
    }

    public String post() {
        return "POST:: Composant_controllerReclamtion";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('Reclamation:update')")
    public String put() {
        return "PUT:: Composant_controllerReclamtion";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('Reclamation:delete')")

    public String delete() {
        return "DELETE:: Composant_controllerReclamtion";
    }








}
