package com.veigadealmeida.projetofinal.controller;


import com.veigadealmeida.projetofinal.dto.usuario.ResetSenhaDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEsqueceuSenhaDTO;
import com.veigadealmeida.projetofinal.services.AlterarSenhaTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@CrossOrigin
@RequestMapping("/resetsenha")
public class AlterarSenhaTokenController {

    private final AlterarSenhaTokenService alterarSenhaTokenService;

    public AlterarSenhaTokenController(AlterarSenhaTokenService alterarSenhaTokenService){
        this.alterarSenhaTokenService = alterarSenhaTokenService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetSenhaDTO resetSenhaDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        alterarSenhaTokenService.resetSenha(request, resetSenhaDTO.email());
        return ResponseEntity.ok().body("Um email foi enviado para o endereço: " + resetSenhaDTO.email());
    }

    @GetMapping("/exibir/{token}")
    public Boolean exibeTrocaDeSenha(@PathVariable String token){
        return alterarSenhaTokenService.exibeTrocaDeSenha(token);
    }

    @PostMapping("/salvar")
    public ResponseEntity<String> salvaTrocaDeSenha(@RequestBody @Valid UsuarioEsqueceuSenhaDTO passwordDto){
         alterarSenhaTokenService.salvaTrocaDeSenha(passwordDto);
        return ResponseEntity.ok().body("Senha atualizada com sucesso!");
    }
}
