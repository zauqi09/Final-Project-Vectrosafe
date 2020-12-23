package com.vectrosafe.repository;

import com.vectrosafe.model.NomorXL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IsiPulsaXL extends JpaRepository<NomorXL, Long> {

    @Query("SELECT x FROM NomorXL x WHERE x.no_hp = ?1")
    Optional<NomorXL> findByNumber(String fooIn);
}
