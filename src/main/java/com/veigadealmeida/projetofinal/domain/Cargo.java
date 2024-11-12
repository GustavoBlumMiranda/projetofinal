package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.cargo.CargoDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "Cargo")
@Entity(name="Cargo")
public class Cargo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricaoCargo;

    public Cargo(CargoDTO cargoDTO){
        this.descricaoCargo = cargoDTO.descricaoCargo();
    }

}
