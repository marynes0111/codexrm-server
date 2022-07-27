package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, String> {
}
