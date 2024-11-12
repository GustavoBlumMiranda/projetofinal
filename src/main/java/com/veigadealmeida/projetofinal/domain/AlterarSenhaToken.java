package com.veigadealmeida.projetofinal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Table(name = "AlterarSenhaToken")
@Entity(name="AlterarSenhaToken")
public class AlterarSenhaToken extends BaseEntity{

    @Transient
    private final Integer minutosExpiracao = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id")
    private Usuario usuario;

    private Date dataExpiracao;

    public AlterarSenhaToken(Usuario user, String token) {
        this.usuario = user;
        this.token = token;
    }

    public Boolean tokenExpirado(AlterarSenhaToken tokenSenha) {
        final Calendar cal = Calendar.getInstance();
        return tokenSenha.getDataExpiracao().before(cal.getTime());
    }
}
