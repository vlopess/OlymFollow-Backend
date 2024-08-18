package com.OlymFollow.Backend.Repositories;

import com.OlymFollow.Backend.Entitys.Country;
import com.OlymFollow.Backend.Entitys.Esporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsporteRepository extends JpaRepository<Esporte, Long> {
    Optional<Esporte> findByNome(String sportName);

}
