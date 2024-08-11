package com.OlymFollow.Backend.Services;

import com.OlymFollow.Backend.Entitys.Esporte;
import com.OlymFollow.Backend.Repositories.EsporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsporteService {
    private EsporteRepository repository;
    @Autowired
    public EsporteService(EsporteRepository repository) {
        this.repository = repository;
    }
    public Esporte salvar(Esporte esporte) {
        var sport = repository.findByNome(esporte.getNome());
        if (sport.isEmpty()) {
            return repository.save(esporte);
        }
        return esporte;
    }
    public Esporte buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<Esporte> buscarTodos() {
        return repository.findAll();
    }
}
