package com.example.lab3.entity;

import lombok.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//import static com.example.lab3.App.vladelysDao;


@Entity
@Table(name = "tehnika")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Data
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = "vladeltsy")
@EqualsAndHashCode(exclude = "vladeltsy")
public abstract class Tehnika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double ves;

    @Column(nullable = false)
    private String color;



    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "tehnika_vladeltsy",
            joinColumns = @JoinColumn(name = "tehnika_id"),
            inverseJoinColumns = @JoinColumn(name = "vladel_id")
    )
    private Set<Vladelys> vladeltsy = new HashSet<>();

    @Override
    public String toString() {
        String vladeltsyString = "";
        for (Vladelys vl : vladeltsy) {
            vladeltsyString += vl.getName() + " ";
        }
        return vladeltsyString;
    }



    // Действия
    public String ehatVpered() {
        return model + ": едет вперед.";
    }

    public String ehatNazad() {
        return model + ": едет назад.";
    }

    public abstract String signal();
    public abstract String obsluzhivanie();
}
