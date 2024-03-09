package com.centranord.Services;

import com.centranord.Entity.Reclamation;
import com.centranord.Repository.ReclamationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationService   {
    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }
     public Reclamation addReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }
}
