package com.OlymFollow.Backend.Repositories;

import com.OlymFollow.Backend.Entitys.Medalha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedalRepository extends JpaRepository<Medalha, Long> {
    List<Medalha> findAllByOrderByIdAsc();
}
