package com.OlymFollow.Backend.Repositories;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Medalha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Medalha> findMedalhasByNome(String countryName);

}
