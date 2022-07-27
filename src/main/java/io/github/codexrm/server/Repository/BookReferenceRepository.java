package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.BookReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReferenceRepository extends JpaRepository<BookReference, String> {
}
