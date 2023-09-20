package dev.silvestredev.todosimple.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import dev.silvestredev.todosimple.models.enums.ProfileEnum;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSpringSecurity implements UserDetails{
    
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecurity(Long id, String username, String password, Set<ProfileEnum> profileEnums){

        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = profileEnums.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }

    //não expirar conta
    @Override
    public boolean isAccountNonExpired() { 

        return true;
    }

    //conta não está "travada"
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    //credenciais não estão expiradas
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    //conta está ativa?
    @Override
    public boolean isEnabled() {

        return true;
    }

    //validação de tipo de conta (se é admin ou user)
    public boolean hasRole(ProfileEnum profileEnum) {
        
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    }
    
}
