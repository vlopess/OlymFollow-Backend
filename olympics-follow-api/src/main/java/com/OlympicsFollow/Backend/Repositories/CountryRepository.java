package com.OlymFollow.Backend.Repositories;

import com.OlymFollow.Backend.Entitys.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNome(String countryName);
}
