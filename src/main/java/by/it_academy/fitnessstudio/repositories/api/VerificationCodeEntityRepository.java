package by.it_academy.fitnessstudio.repositories.api;

import by.it_academy.fitnessstudio.entity.VerificationCodeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface VerificationCodeEntityRepository extends CrudRepository<VerificationCodeEntity, UUID> {
}
