package com.capstone.library.repository;

import com.capstone.library.model.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    Optional<Catalogue> findById(Catalogue id);

    Optional<Catalogue> findByCatalogue(Catalogue catalogue);


}
