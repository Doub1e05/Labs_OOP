package com.example.lab3.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vladeltsy")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "tehniki")
@EqualsAndHashCode(exclude = "tehniki")

public class Vladelys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "vladeltsy")
    private Set<Tehnika> tehniki =  new HashSet<>();


}
