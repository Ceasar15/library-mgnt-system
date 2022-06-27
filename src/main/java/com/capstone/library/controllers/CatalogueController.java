package com.capstone.library.controllers;

import com.capstone.library.model.Catalogue;
import com.capstone.library.repository.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("catalogue/")
public class CatalogueController {
    //    for logging purposes
    private static final Logger logger = LoggerFactory.getLogger(CatalogueController.class);


    @Autowired
    CatalogueRepository catalogueRepository;

    public CatalogueController(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @PostMapping("/createCatalogue")
    public ResponseEntity<Catalogue> createCatalogue(@RequestBody Catalogue catalogue) {
        try {
            Catalogue catalogue1 = catalogueRepository.save(new Catalogue(catalogue.getCatalogue()));
            return new ResponseEntity<>(catalogue1, HttpStatus.CREATED);
        } catch (Exception e) {
//            logger.info("Error: " + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCatalogue")
    public ResponseEntity<List<Catalogue>> getCatalogue() {
        try {
            List<Catalogue> allCatalogues;
            allCatalogues = catalogueRepository.findAll();
            return new ResponseEntity<>(allCatalogues, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
