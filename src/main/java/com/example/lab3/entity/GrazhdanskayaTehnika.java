package com.example.lab3.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Grazhdanskaya")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GrazhdanskayaTehnika extends Tehnika {
    @Column(name = "podogrev_rulya")
    private boolean podogrevRulya;

    @Column(name = "golosovoy_assistent")
    private boolean golosovoyAssistent;

    @Override
    public String signal() {
        return getModel() + ": бип-бип!";
    }

    @Override
    public String obsluzhivanie() {
        return getModel() + ": ТО у официального дилера!";
    }

    public String vklyuchitAvariyku() {
        return getModel() + ": Аварийка включена.";
    }

    public String ispolzovatKakTaxi() {
        return getModel() + ": Используется как такси";
    }
}
