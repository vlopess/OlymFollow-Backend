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

    @Autowired
    private EmailClient emailClient;

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

        // Obter o email do usuário autenticado
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // User user = (User) authentication.getPrincipal();
        // String emailUsuario = user.getEmail();

        // System.out.println(emailUsuario);

        Email email = new Email();
        email.setMailFrom("segiocerq11@gmail.com");
        email.setMailTo("segiocerq11@gmail.com");
        email.setMailSubject("Um país que você segue ganhou uma nova medalha!");
        email.setMailText(
            "OlympicsFollow informa: " +
            medal.getNomeAtleta() +
            " do "  + medaldto.country().getNome() +
            " ganhou uma medalha de " +
            medaldto.medalha().name() +
            " no(a) " +
            medaldto.esporte().getNome() +
            "."
        );
        emailClient.sendEmail(new EmailDTO(email));
        return medalRepository.save(medal);

    }
}