package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.domain.Cargo;
import com.veigadealmeida.projetofinal.dto.cargo.CargoDTO;
import com.veigadealmeida.projetofinal.dto.cargo.CargoDetalhamentoDTO;
import com.veigadealmeida.projetofinal.repository.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository){;
        this.cargoRepository = cargoRepository;
    }

    public CargoDetalhamentoDTO cadastrarCargo(CargoDTO cargoDTO){
        Cargo cargo = new Cargo(cargoDTO);
        cargo.setCadastro(cargo);
        cargo = cargoRepository.save(cargo);
        return new CargoDetalhamentoDTO(cargo);
    }
}
