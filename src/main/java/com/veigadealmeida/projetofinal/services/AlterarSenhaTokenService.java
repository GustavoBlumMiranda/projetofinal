package com.veigadealmeida.projetofinal.services;


import com.veigadealmeida.projetofinal.configuration.security.SecurityFilter;
import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.controller.customexceptions.EntityNotFoundException;
import com.veigadealmeida.projetofinal.domain.AlterarSenhaToken;
import com.veigadealmeida.projetofinal.domain.Usuario;

import com.veigadealmeida.projetofinal.dto.usuario.UsuarioAlteraSenhaDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEsqueceuSenhaDTO;
import com.veigadealmeida.projetofinal.repository.TokenResetSenhaRepository;
import com.veigadealmeida.projetofinal.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
@Service
public class AlterarSenhaTokenService {
    private final UsuarioRepository usuarioRepository;
    private final TokenResetSenhaRepository tokenResetSenhaRepository;
    private final MailSenderService mailSender;
    private final Environment env;
    private final MessageSource messages;
    private final UsuarioService usuarioService;
    private final SecurityFilter securityFilter;


    public AlterarSenhaTokenService(UsuarioRepository usuarioRepository, TokenResetSenhaRepository tokenResetSenhaRepository,
                                    MailSenderService mailSender, Environment env, MessageSource messages,
                                    SecurityFilter securityFilter, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenResetSenhaRepository = tokenResetSenhaRepository;
        this.mailSender = mailSender;
        this.env = env;
        this.messages = messages;
        this.securityFilter = securityFilter;
        this.usuarioService = usuarioService;
    }
    public void resetSenha(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Usuario user = usuarioRepository.findByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("Não foi encontrado um usuário com o email: " + userEmail);
        }
        String token = UUID.randomUUID().toString();
        this.geraTokenResetSenha(user, token);
        mailSender.sendPasswordResetEmail(getAppUrl(request), request.getLocale(), token, user);
    }

    @Transactional
    public void geraTokenResetSenha(Usuario user, String token) {
        AlterarSenhaToken myToken = new AlterarSenhaToken(user, token);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, myToken.getMinutosExpiracao());
        myToken.setDataExpiracao(calendar.getTime());
        myToken.setCadastro(myToken);
        tokenResetSenhaRepository.save(myToken);
    }

    public String getAppUrl(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }
        return baseUrl;
    }

    public Boolean exibeTrocaDeSenha(String token) {
        return securityFilter.validaTrocaDeSenha(token);
    }

    public void salvaTrocaDeSenha(UsuarioEsqueceuSenhaDTO usuarioEsqueceuSenhaDTO) {
        if(!securityFilter.validaTrocaDeSenha(usuarioEsqueceuSenhaDTO.token())) {
            throw new BadRequestException("Falha ao validar o token de troca de senha");
        }
        AlterarSenhaToken alterarSenhaToken = tokenResetSenhaRepository.findByToken(usuarioEsqueceuSenhaDTO.token());
        Usuario user = null;
        if(alterarSenhaToken != null && alterarSenhaToken.getUsuario() != null){
            user = alterarSenhaToken.getUsuario();
        }
        if(user != null) {
            UsuarioAlteraSenhaDTO usuarioAlteraSenhaDTO = new UsuarioAlteraSenhaDTO(usuarioEsqueceuSenhaDTO);
            usuarioService.alterarSenha(usuarioAlteraSenhaDTO, user);
        }else{
            throw new BadRequestException("Falha ao encontrar usuário associado a token");
        }
    }
}
