package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.ThesisReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisReferenceRepository extends JpaRepository<ThesisReference, String> {
}
