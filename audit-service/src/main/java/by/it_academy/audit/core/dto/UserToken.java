package by.it_academy.audit.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
@JsonPropertyOrder({"uuid", "mail", "fio", "role"})
public class UserToken implements UserDetails {
    private String mail;
    private UserRole role;
    private String fio;
    private UUID uuid;

    public UserToken() {
    }

    public UserToken(String mail, UserRole role, String fio, UUID uuid) {
        this.mail = mail;
        this.role = role;
        this.fio = fio;
        this.uuid = uuid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFio() {
        return fio;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authority;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return fio;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
