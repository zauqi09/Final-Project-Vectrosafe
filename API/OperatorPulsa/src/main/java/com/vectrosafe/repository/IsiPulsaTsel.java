package com.vectrosafe.repository;

import com.vectrosafe.model.NomorTsel;
import com.vectrosafe.model.NomorXL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IsiPulsaTsel extends JpaRepository<NomorTsel, Long> {
    @Query("SELECT x FROM NomorTsel x WHERE x.no_hp = ?1")
    Optional<NomorTsel> findByNumber(String fooIn);

}
