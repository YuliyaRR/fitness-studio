package by.it_academy.audit.repositories.api;

import by.it_academy.audit.entity.AuditEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AuditEntityRepository extends CrudRepository<AuditEntity, UUID>, PagingAndSortingRepository<AuditEntity, UUID> {
}
