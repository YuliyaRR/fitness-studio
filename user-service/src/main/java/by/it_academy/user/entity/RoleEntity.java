package by.it_academy.user.entity;

import by.it_academy.user.core.dto.user.UserRole;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "app", name = "role")
public class RoleEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public RoleEntity() {
    }

    public RoleEntity(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
