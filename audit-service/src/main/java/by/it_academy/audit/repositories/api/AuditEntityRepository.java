package by.it_academy.audit.repositories.api;

import by.it_academy.audit.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AuditEntityRepository extends JpaRepository<AuditEntity, UUID> {
}
