package com.veigadealmeida.projetofinal.domain;

import com.veigadealmeida.projetofinal.dto.usuario.UsuarioDTO;
import com.veigadealmeida.projetofinal.dto.usuario.UsuarioEditarDTO;
import com.veigadealmeida.projetofinal.enumerators.TipoUsuarioEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoUsuario")
    private TipoUsuarioEnum tipoUsuario;

    private String nome;

  @Column (name = "email")
    private String email;

    private String cpf;
    private String codUsuario;
    private String login;
    private String password;
    private Boolean ativo;

    @ManyToMany(mappedBy = "usuarios")
    private List<Projeto> projetos = new ArrayList<>();

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
        return List.of(new SimpleGrantedAuthority(tipoUsuario.getDescricao()));
    }

    public Usuario(UsuarioDTO usuarioDTO){
        this.tipoUsuario = TipoUsuarioEnum.valueOf(usuarioDTO.tipoUsuario().toUpperCase());
        this.cpf = usuarioDTO.cpf();
        this.nome = usuarioDTO.nome();
        this.email = usuarioDTO.email();
        this.codUsuario = usuarioDTO.codUsuario();
        this.login = usuarioDTO.login();
        this.password = usuarioDTO.password();
        this.ativo = usuarioDTO.ativo();
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
