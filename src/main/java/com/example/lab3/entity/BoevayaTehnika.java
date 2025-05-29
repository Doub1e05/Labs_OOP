package com.example.lab3.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@DiscriminatorValue("Boevaya")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class BoevayaTehnika extends Tehnika {

    @Column(name = "ognevaya_moschnost")
    private int ognevayaMoschnost;

    @Column(name = "obem_oboymy")
    private int obemOboymy;

    @Override
    public String signal() {
        return getModel() + ": Издал боевой сигнал!";
    }

    @Override
    public String obsluzhivanie() {
        return getModel() + ": Выполнил обслуживание в ангаре.";
    }

    public String vystrel() {
        return getModel() + ": Выполнил выстрел.";
    }

    public String perezaryadka() {
        return getModel() + ": Выполнил перезарядку";
    }
}
