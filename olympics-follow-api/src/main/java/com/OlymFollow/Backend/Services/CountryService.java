package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Dtos.CountryDTO;
import com.OlymFollow.Backend.Dtos.CountryDetailsDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Repositories.CountryRepository;
import com.OlymFollow.Backend.Security.infra.NotFoundException;
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

    public List<CountryDetailsDTO> getAllCountriesWithMedals() {
        return countryRepository.findAll().stream().map(CountryDetailsDTO::new).toList();
    }

    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream().map(CountryDTO::new).toList();
    }

    public Country getCountryById(int id) {
        return unwrap(countryRepository.findById((long) id));
    }

    public CountryDetailsDTO getCountryByName(String name) {
        var country = countryRepository.findByNome(name);
        return new CountryDetailsDTO(unwrap(country));
    }

    public Country salvar(Country country) {
        Optional<Country> countryExists = countryRepository.findByNome(country.getNome());
        return countryExists.orElseGet(() -> countryRepository.save(country));
    }

    static Country unwrap(Optional<Country> country) {
        if(country.isPresent()) return country.get();
        throw new NotFoundException("Country not found");
    }
}
