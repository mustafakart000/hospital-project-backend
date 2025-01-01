package com.hospital.initializer;

import com.hospital.model.Laboratuvar;
import com.hospital.entity.Laboratuary;
import com.hospital.repository.LaboratuvarRepository;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class LabaratuvarInitializer implements CommandLineRunner {
    private final LaboratuvarRepository repository;
    
    public LabaratuvarInitializer(LaboratuvarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        try {
                for (Laboratuvar laboratuvar : Laboratuvar.values()) {
                if (!repository.existsByLaboratuvar(laboratuvar)) {
                    Laboratuary laboratuary = new Laboratuary();
                    laboratuary.setLaboratuvar(laboratuvar);
                    laboratuary.setDisplayName(laboratuvar.getDisplayName());
                    repository.save(laboratuary);
                    System.out.println("Saved laboratuvar: " + laboratuvar);
                } else {
                    System.out.println("Laboratuvar already exists: " + laboratuvar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
