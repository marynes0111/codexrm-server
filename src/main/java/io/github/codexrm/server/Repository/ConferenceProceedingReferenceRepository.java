package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.ConferenceProceedingReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceProceedingReferenceRepository extends JpaRepository<ConferenceProceedingReference, String> {
}
