package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Dtos.MedalDTO;
import com.OlymFollow.Backend.Entitys.Medalha;
import com.OlymFollow.Backend.Repositories.MedalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalService {
    private MedalRepository medalRepository;

    @Autowired
    public MedalService(MedalRepository medalRepository) {
        this.medalRepository = medalRepository;
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
        var medal = new Medalha(medaldto);
        return medalRepository.save(medal);
    }
}
