package by.it_academy.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "verification")
public class VerificationCodeEntity {
    @Id
    private UUID code;
    private String email;
    public VerificationCodeEntity() {
    }
    public VerificationCodeEntity(UUID code, String email) {
        this.code = code;
        this.email = email;
    }
    public UUID getCode() {
        return code;
    }
    public void setCode(UUID code) {
        this.code = code;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationCodeEntity that = (VerificationCodeEntity) o;
        return Objects.equals(code, that.code) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, email);
    }
}
