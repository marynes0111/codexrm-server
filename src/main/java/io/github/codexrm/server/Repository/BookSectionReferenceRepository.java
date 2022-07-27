package io.github.codexrm.server.Repository;

import io.github.codexrm.server.Model.BookSectionReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSectionReferenceRepository extends JpaRepository<BookSectionReference, String> {
}
