package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(int id) {
        return countryRepository.findById((long) id);
    }

    public MedalDTO getMedalByName(String name) {
        return countryRepository.findMedalhasByNome(name).map(MedalDTO::new).orElse(null);
    }
}
