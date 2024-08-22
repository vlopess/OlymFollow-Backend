package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Clients.EmailClient;
import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Repositories.MedalRepository;
import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedalService {

    private final CountryService countryService;
    private final MedalRepository medalRepository;
    private final EsporteService esporteService;

    @Autowired
    private EmailClient emailClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        Country country = countryService.salvar(medaldto.country());
        Esporte esporte = esporteService.salvar(medaldto.esporte());
        Medalha medal = new Medalha(medaldto, esporte, country);

        Map<String, Object> medalData = new HashMap<>();
        medalData.put("nomeAtleta", medaldto.nomeAtleta());
        medalData.put("country", medaldto.country().getNome());
        medalData.put("medalha", medaldto.medalha().name());
        medalData.put("esporte", medaldto.esporte().getNome());

        this.rabbitTemplate.convertAndSend("email-medalha", medalData);
        return medalRepository.save(medal);

    }
}