package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Repositories.EsporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EsporteService {
    private EsporteRepository repository;
    @Autowired
    public EsporteService(EsporteRepository repository) {
        this.repository = repository;
    }

    public Esporte salvar(Esporte esporte) {
        Optional<Esporte> sport = repository.findByNome(esporte.getNome());
        return sport.orElseGet(() -> repository.save(esporte));
    }

    public Esporte buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<Esporte> buscarTodos() {
        return repository.findAll();
    }

}
