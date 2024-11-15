package com.veigadealmeida.projetofinal.services;

import com.veigadealmeida.projetofinal.configuration.security.TokenJWTService;
import com.veigadealmeida.projetofinal.controller.customexceptions.BadRequestException;
import com.veigadealmeida.projetofinal.controller.customexceptions.ObjectNotFoundException;
import com.veigadealmeida.projetofinal.domain.Usuario;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioAlteraSenhaDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDetalhamentoDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEditarDTO;
import com.veigadealmeida.projetofinal.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenJWTService tokenJWTService;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenJWTService tokenJWTService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenJWTService = tokenJWTService;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDetalhamentoDTO getUsuarioPorId(Long id) {
        Usuario usuario;

        if(id == null) {
            String subject = tokenJWTService.getSubject(TokenJWTService.getBearerTokenHeader());
            usuario = usuarioRepository.findByLogin(subject);
            return new UsuarioDetalhamentoDTO(usuario);
        }
        
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        usuario = optionalUsuario.orElseThrow(() -> new ObjectNotFoundException("Id do usuario não encontrado: " + id));

        return new UsuarioDetalhamentoDTO(usuario);
    }

    public Page listarUsuario(Pageable paginacao) {
        return usuarioRepository.findAll(paginacao).map(UsuarioDetalhamentoDTO::new);
    }

    @Transactional
    public UsuarioDetalhamentoDTO alterarSenha(UsuarioAlteraSenhaDTO usuarioAlteraSenhaDTO, Usuario usuarioDeslogado) {
        Usuario usuario = usuarioDeslogado;
        if(usuario == null){
            String login = tokenJWTService.getSubject(TokenJWTService.getBearerTokenHeader());
            usuario = usuarioRepository.findByLogin(login);
        }
        this.validaAlteracaoSenha(usuario, usuarioAlteraSenhaDTO, usuarioDeslogado);
        usuario.setPassword(passwordEncoder.encode(usuarioAlteraSenhaDTO.senhaNova()));
        usuario = usuarioRepository.save(usuario);
        return new UsuarioDetalhamentoDTO(usuario);
    }

    @Transactional
    public UsuarioDetalhamentoDTO cadastrarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.password()));
        usuario = usuarioRepository.save(usuario);
        return new UsuarioDetalhamentoDTO(usuario);
    }

    public void Delete(long id) {
        Optional<Usuario> Usuario = usuarioRepository.findById(id);

        if(Usuario.isEmpty()){
            throw new ObjectNotFoundException("Id do usuário não encontrado: " + id);
        }

        usuarioRepository.delete(Usuario.get());
    }

    public void validaAlteracaoSenha(Usuario usuario, UsuarioAlteraSenhaDTO usuarioAlteraSenhaDTO, Usuario usuarioDeslogado) {
        String senhaAtual;
        if(usuarioDeslogado == null){
            senhaAtual = usuarioRepository.findById(usuario.getId()).get().getPassword();
        } else {
            senhaAtual = usuarioRepository.findById(usuarioDeslogado.getId()).get().getPassword();
        }
        String senhaNova = usuarioAlteraSenhaDTO.senhaNova();
        String senhaConfirmaNovaSenha = usuarioAlteraSenhaDTO.senhaConfirmaNovaSenha();
        if (passwordEncoder.matches(senhaNova, senhaAtual)) {
            throw new BadRequestException("Senha atual e nova senha são iguais");
        }
        if (!senhaNova.equals(senhaConfirmaNovaSenha)) {
            throw new BadRequestException("Nova senha e confirmação de senha não são iguais");
        }
    }

    @Transactional
    public UsuarioDetalhamentoDTO editarUsuario(UsuarioEditarDTO usuarioEditarDTO){
        Usuario usuario = usuarioRepository.findById(usuarioEditarDTO.id()).orElseThrow(() -> new ObjectNotFoundException("Id do usuário não encontrado: " + usuarioEditarDTO.id()));
        this.verificaUsuarioDuplicado(usuarioEditarDTO);
        return new UsuarioDetalhamentoDTO(usuario.atualizaUsuario(usuario, usuarioEditarDTO));
    }

    public void verificaUsuarioDuplicado(UsuarioEditarDTO usuarioEditarDTO){
        List<Usuario> listaUser;
        listaUser = usuarioRepository.findAllByLogin(usuarioEditarDTO.login());
        if(usuarioEditarDTO.login() != null && listaUser.size()>1 || !listaUser.get(0).getId().equals(usuarioEditarDTO.id())){
            throw new BadRequestException("Usuário com o login informado já cadastrado");
        }

        listaUser.clear();
        listaUser = usuarioRepository.findAllByEmail(usuarioEditarDTO.email());
        if(usuarioEditarDTO.email() != null && listaUser.size()>1 || !listaUser.get(0).getId().equals(usuarioEditarDTO.id())){
            throw new BadRequestException("Usuário com o e-mail informado já cadastrado");
        }

        listaUser.clear();
        listaUser = usuarioRepository.findAllByCpf(usuarioEditarDTO.cpf());
        if(usuarioEditarDTO.cpf() != null && listaUser.size()>1 || !listaUser.get(0).getId().equals(usuarioEditarDTO.id())){
            throw new BadRequestException("Usuário com o cpf informado já cadastrado");
        }

        listaUser.clear();
        listaUser = usuarioRepository.findAllByCodUsuario(usuarioEditarDTO.codUsuario());
        if(usuarioEditarDTO.codUsuario() != null && listaUser.size()>1 || !listaUser.get(0).getId().equals(usuarioEditarDTO.id())){
            throw new BadRequestException("Usuário com o código informado já cadastrado");
        }
    }

}
