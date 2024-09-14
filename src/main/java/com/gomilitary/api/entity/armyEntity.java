package com.gomilitary.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="teukgiCode")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class armyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    //@OneToMany(mappedBy ="abcd", cascade = CascadeType.ALL, orphanRemoval = true)
    private Double teukgiCode;
    private String teukgiCodeStr;
    
    private String gunGubun;
    private String teukgiName;

}
