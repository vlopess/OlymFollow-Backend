package com.OlymFollow.Backend.Repositories;

import com.OlymFollow.Backend.Entitys.Medalha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedalRepository extends JpaRepository<Medalha, Long> {
    List<Medalha> findAllByOrderByIdAsc();
}
