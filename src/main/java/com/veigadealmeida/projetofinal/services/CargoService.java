package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.domain.Grupo;
import com.veigadealmeida.projetofinal.dto.grupo.GrupoDTO;
import com.veigadealmeida.projetofinal.dto.grupo.GrupoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoService {

    /*private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository){;
        this.cargoRepository = cargoRepository;
    }*/

    /*public GrupoDetalhamentoDTO cadastrarCargo(GrupoDTO grupoDTO){
        Grupo cargo = new Grupo(grupoDTO);
        cargo.setCadastro(cargo);
        cargo = cargoRepository.save(cargo);
        return new GrupoDetalhamentoDTO(cargo);
    }*/
}
