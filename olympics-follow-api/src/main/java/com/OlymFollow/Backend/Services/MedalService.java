package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Repositories.MedalRepository;
import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalService {

    private final CountryService countryService;
    private final MedalRepository medalRepository;
    private final EsporteService esporteService;
    private final EmailService emailService;
    private final UserService userService;


    @Autowired
    public MedalService(MedalRepository medalRepository, CountryService countryService, EsporteService esporteService, EmailService emailService, UserService userService) {
        this.medalRepository = medalRepository;
        this.countryService = countryService;
        this.esporteService = esporteService;
        this.emailService = emailService;
        this.userService = userService;
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
        Country country = countryService.getCountryById(Integer.parseInt(medaldto.countryID()));
        Esporte esporte = esporteService.salvar(new Esporte(medaldto.esporte()));

        Medalha medal = new Medalha(medaldto, esporte, country);


        var users = userService.getUsersWhatFollowsCountry(country.getNome());
        users.forEach(userDTO -> {
            Email email = new Email();
            email.setMailTo(userDTO.getEmail());
            email.setMailSubject("Um país que você segue ganhou uma nova medalha!");
            email.setMailText(
                    "OlympicsFollow informa: " +
                            medal.getNomeAtleta() +
                            " do(a) "  + medal.getCountry().getNome() +
                            " ganhou uma medalha de " +
                            medal.getMedalha() +
                            " no(a) " +
                            medal.getEsporte().getNome() +
                            "."
            );
            emailService.sendEmail(email);
        });
        return medalRepository.save(medal);

    }
}