package com.gomilitary.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gomilitary.api.entity.teukgiCodeEntity;

public interface teukgiCodeApiRepository extends JpaRepository<teukgiCodeEntity, Integer> {

    boolean existsByTeukgiCode(Double teukgiCode);
    boolean existsByTeukgiCodeStr(String teukgiCodeStr);
    
}