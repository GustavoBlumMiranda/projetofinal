package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEditarDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "usuario")
@Entity(name="Usuario")
public class Usuario extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    private String nome;
    private String telefone;

  @Column (name = "eMail")
    private String email;

    private String cpf;
    private String codUsuario;
    private String login;
    private String password;
    private Boolean ativo;
    private String sobrenome;
    private String empresa;
    private String cidade;
    private String estado;
    private Date nascimento;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(cargo.getDescricaoCargo()));
    }

    public Usuario(UsuarioDTO usuarioDTO){
        this.cpf = usuarioDTO.cpf();
        this.nome = usuarioDTO.nome();
        this.telefone = usuarioDTO.telefone();
        this.email = usuarioDTO.email();
        this.codUsuario = usuarioDTO.codUsuario();
        this.login = usuarioDTO.login();
        this.password = usuarioDTO.password();
        this.ativo = usuarioDTO.ativo();
        this.sobrenome = usuarioDTO.sobrenome();
        this.empresa = usuarioDTO.empresa();
        this.cidade = usuarioDTO.cidade();
        this.estado = usuarioDTO.estado();
        this.nascimento = usuarioDTO.nascimento();
    }

    public Usuario atualizaUsuario(Usuario usuario, UsuarioEditarDTO usuarioEditarDTO) {
        Class<?> usuarioEditarDTOClass = usuarioEditarDTO.getClass();
        Field[] campos = usuarioEditarDTOClass.getDeclaredFields();

        for (Field campo : campos) {
            try {
                campo.setAccessible(true);
                Object valorCampo = campo.get(usuarioEditarDTO);

                if (valorCampo != null) {
                    Field usuarioField = usuario.getClass().getDeclaredField(campo.getName());
                    usuarioField.setAccessible(true);
                    usuarioField.set(usuario, valorCampo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }



}
