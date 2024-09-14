package com.gomilitary.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gomilitary.api.entity.armyEntity;

public interface ArmyRepository extends JpaRepository<armyEntity, Integer> {

    boolean existsByTeukgiCode(Double teukgiCode);

    boolean existsByTeukgiCodeStr(String teukgiCodeStr);
    
}
