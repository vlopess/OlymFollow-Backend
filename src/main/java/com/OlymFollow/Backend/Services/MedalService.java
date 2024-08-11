package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Dtos.MedalRegisterDTO;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Repositories.MedalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalService {
    private final CountryService countryService;
    private final MedalRepository medalRepository;
    private final EsporteService esporteService;

    @Autowired
    public MedalService(MedalRepository medalRepository, CountryService countryService, EsporteService esporteService) {
        this.medalRepository = medalRepository;
        this.countryService = countryService;
        this.esporteService = esporteService;
    }

    public List<MedalDTO> getAllMedals() {
        var medals = medalRepository.findAll();
        return medals.stream().map(MedalDTO::new).toList();
    }
    public List<MedalDTO> getLastestMedals() {
        var medals = medalRepository.findAllByOrderByIdAsc();
        return medals.stream().map(MedalDTO::new).toList();
    }

    public MedalDTO getMedalById(int id) {
        var medal =  medalRepository.getReferenceById((long) id);
        return new MedalDTO(medal);
    }


    public Medalha addMedal(MedalDTO medaldto) {
        var country = countryService.salvar(medaldto.country());
        var esporte =  esporteService.salvar(medaldto.esporte());
        var medal = new Medalha(medaldto);
        return medalRepository.save(medal);
    }
}
