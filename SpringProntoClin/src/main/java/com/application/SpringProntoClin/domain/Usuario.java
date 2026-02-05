package com.application.SpringProntoClin.domain;

import com.application.SpringProntoClin.enums.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario  implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq_generator")
    @SequenceGenerator(name = "usuario_seq_generator", sequenceName = "usuario_SEQ", allocationSize = 1)
    private Long iduser;

    @Column(unique = true)
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "userrole")
    private UsuarioRole userrole;

    public Usuario(String email, String senha, UsuarioRole userrole) {
        this.email = email;
        this.senha = senha;
        this.userrole = userrole;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userrole == UsuarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if(this.userrole == UsuarioRole.PACIENTE) return List.of(new SimpleGrantedAuthority("ROLE_PACIENTE"));
        if(this.userrole == UsuarioRole.PROFSAUDE) return List.of(new SimpleGrantedAuthority("ROLE_PROFSAUDE"));
        else return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
